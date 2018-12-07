package prc.utils;


import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import prc.autodoc.Data_2da;
import prc.autodoc.Autodoc.TLKStore;
import prc.autodoc.Autodoc.TwoDAStore;


/**
 *  A little tool that parses des_crft_scroll, extracts unique item resrefs from it and
 *  makes a merchant selling those resrefs.
 *
 * @author Heikki 'Ornedan' Aitakangas
 */
public class ScrollMerchantGen {
	private static Logger LOGGER = LoggerFactory.getLogger(ScrollMerchantGen.class);

	/**
	 * Ye olde maine methode.
	 * 
	 * @param args         The arguments
	 * @throws IOException If the writing fails, just die on the exception
	 */
	public static void main(String[] args) throws IOException {
		if(args.length == 0) readMe();
		String twoDAPath = null;
		String tlkPath   = null;

		// parse args
		for(String param : args) {//2dadir tlkdir | [--help]
			// Parameter parseage
			if(param.startsWith("-")) {
				if(param.equals("--help")) readMe();
				else{
					for(char c : param.substring(1).toCharArray()) {
						switch(c) {
						default:
							LOGGER.error("Unknown parameter: " + c);
							readMe();
						}
					}
				}
			}
			else {
				// It's a pathname
				if(twoDAPath == null)
					twoDAPath = param;
				else if(tlkPath == null)
					tlkPath = param;
				else{
					LOGGER.error("Unknown parameter: " + param);
					readMe();
				}
			}
		}
		
		// Load data
		TwoDAStore twoDA = new TwoDAStore(twoDAPath);
		TLKStore    tlks = new TLKStore("dialog.tlk", "prc_consortium.tlk", tlkPath);
		
		doScrollMerchantGen(twoDA, tlks);
	}
	
	/**
	 * Performs the scroll merchant generation. Made public for the purposes of BuildScrollHack.
	 * 
	 * @param twoDA        A TwoDAStore for loading 2da data from
	 * @param tlks         A TLKStore for reading tlk data from
	 * @throws IOException Just tossed back up
	 */
	public static void doScrollMerchantGen(TwoDAStore twoDA, TLKStore tlks) throws IOException {
		// Load the 2da file
		Data_2da scrolls2da = twoDA.get("des_crft_scroll");
		Data_2da spells2da  = twoDA.get("spells");
		

		// Loop over the scroll entries and get a list of unique resrefs
		TreeMap<Integer, TreeMap<String, String>> arcaneScrollResRefs = new TreeMap<Integer, TreeMap<String, String>>();
		TreeMap<Integer, TreeMap<String, String>> divineScrollResRefs = new TreeMap<Integer, TreeMap<String, String>>();
		String entry;
		for(int i = 0; i < scrolls2da.getEntryCount(); i++) {
			// Skip subradials
			if(spells2da.getEntry("Master", i).equals("****")) {
				if(!(entry = scrolls2da.getEntry("Wiz_Sorc", i)).equals("****"))
					addScroll(arcaneScrollResRefs, spells2da, tlks, i, entry.toLowerCase());
				if(!(entry = scrolls2da.getEntry("Cleric", i)).equals("****"))
					addScroll(divineScrollResRefs, spells2da, tlks, i, entry.toLowerCase());
				if(!(entry = scrolls2da.getEntry("Paladin", i)).equals("****"))
					addScroll(divineScrollResRefs, spells2da, tlks, i, entry.toLowerCase());
				if(!(entry = scrolls2da.getEntry("Druid", i)).equals("****"))
					addScroll(divineScrollResRefs, spells2da, tlks, i, entry.toLowerCase());
				if(!(entry = scrolls2da.getEntry("Ranger", i)).equals("****"))
					addScroll(divineScrollResRefs, spells2da, tlks, i, entry.toLowerCase());
				if(!(entry = scrolls2da.getEntry("Bard", i)).equals("****"))
					addScroll(arcaneScrollResRefs, spells2da, tlks, i, entry.toLowerCase());
			}
		}

		String xmlPrefix =
"<gff name=\"prc_scrolls.utm\" type=\"UTM \" version=\"V3.2\" >"        + "\n" +
"    <struct id=\"-1\" >"                                               + "\n" +
"        <element name=\"ResRef\" type=\"11\" value=\"prc_scrolls\" />" + "\n" +
"        <element name=\"LocName\" type=\"12\" value=\"64113\" >"       + "\n" +
"            <localString languageId=\"0\" value=\"prc_scrolls\" />"    + "\n" +
"        </element>"                                                    + "\n" +
"        <element name=\"Tag\" type=\"10\" value=\"prc_scrolls\" />"    + "\n" +
"        <element name=\"MarkUp\" type=\"5\" value=\"100\" />"          + "\n" +
"        <element name=\"MarkDown\" type=\"5\" value=\"100\" />"        + "\n" +
"        <element name=\"BlackMarket\" type=\"0\" value=\"0\" />"       + "\n" +
"        <element name=\"BM_MarkDown\" type=\"5\" value=\"15\" />"      + "\n" +
"        <element name=\"IdentifyPrice\" type=\"5\" value=\"-1\" />"    + "\n" +
"        <element name=\"MaxBuyPrice\" type=\"5\" value=\"-1\" />"      + "\n" +
"        <element name=\"StoreGold\" type=\"5\" value=\"-1\" />"        + "\n" +
"        <element name=\"OnOpenStore\" type=\"11\" value=\"\" />"       + "\n" +
"        <element name=\"OnStoreClosed\" type=\"11\" value=\"\" />"     + "\n" +
"        <element name=\"WillNotBuy\" type=\"15\" />"                   + "\n" +
"        <element name=\"WillOnlyBuy\" type=\"15\" >"                   + "\n" +
"            <struct id=\"97869\" >"                                    + "\n" +
"                <element name=\"BaseItem\" type=\"5\" value=\"29\" />" + "\n" +
"            </struct>"                                                 + "\n" +
"        </element>"                                                    + "\n" +
"        <element name=\"StoreList\" type=\"15\" >"                     + "\n" +
"            <struct id=\"0\" />"                                       + "\n" +
"            <struct id=\"4\" />"                                       + "\n" +
"            <struct id=\"2\" >"                                        + "\n" +
"                <element name=\"ItemList\" type=\"15\" >"              + "\n";
		String xmlSuffix =
"                </element>"                                            + "\n" +
"			 </struct>"                                                 + "\n" +
"            <struct id=\"3\" />"                                       + "\n" +
"            <struct id=\"1\" />"                                       + "\n" +
"        </element>"                                                    + "\n" +
"        <element name=\"ID\" type=\"0\" value=\"5\" />"                + "\n" +
"        <element name=\"Comment\" type=\"10\" value=\"\" />"           + "\n" +
"    </struct>"                                                         + "\n" +
"</gff>"                                                                + "\n";

		StringBuffer xmlString = new StringBuffer();
		int posCounter = 0;
		// First arcane scrolls
		for(Map<String, String> levelScrollResRefs : arcaneScrollResRefs.values())
			for(String name : levelScrollResRefs.keySet()) {
				String resref = levelScrollResRefs.get(name);
				xmlString.append(
"                    <struct id=\"" + posCounter + "\" >"                                                + "\n" +
"                        <element name=\"InventoryRes\" type=\"11\" value=\"" + resref + "\" />"         + "<!-- " + name + " -->" + "\n" +
"                        <element name=\"Repos_PosX\" type=\"2\" value=\"" + (posCounter % 10) + "\" />" + "\n" +
"                        <element name=\"Repos_Posy\" type=\"2\" value=\"" + (posCounter / 10) + "\" />" + "\n" +
"                        <element name=\"Infinite\" type=\"0\" value=\"1\" />"                           + "\n" +
"                    </struct>"                                                                          + "\n"
						);
				posCounter++;
			}
		// Then divine scrolls
		for(Map<String, String> levelScrollResRefs : divineScrollResRefs.values())
			for(String name : levelScrollResRefs.keySet()) {
				String resref = levelScrollResRefs.get(name);
				xmlString.append(
"                    <struct id=\"" + posCounter + "\" >"                                                + "\n" +
"                        <element name=\"InventoryRes\" type=\"11\" value=\"" + resref + "\" />"         + "<!-- " + name + " -->" + "\n" +
"                        <element name=\"Repos_PosX\" type=\"2\" value=\"" + (posCounter % 10) + "\" />" + "\n" +
"                        <element name=\"Repos_Posy\" type=\"2\" value=\"" + (posCounter / 10) + "\" />" + "\n" +
"                        <element name=\"Infinite\" type=\"0\" value=\"1\" />"                           + "\n" +
"                    </struct>"                                                                          + "\n"
						);
				posCounter++;
			}
		
		File target = new File("prc_scrolls.utm.xml");
		// Clean up old version if necessary
		if(target.exists()) {
			LOGGER.info("Deleting previous version of " + target.getName());
			target.delete();
		}
		LOGGER.info("Writing brand new version of " + target.getName());
		target.createNewFile();

		// Creater the writer and print
		FileWriter writer = new FileWriter(target, true);
		writer.write(xmlPrefix + xmlString.toString() + xmlSuffix);
		// Clean up
		writer.flush();
		writer.close();
	}
	
	private static void addScroll(TreeMap<Integer, TreeMap<String, String>> scrollResRefs,
			                      Data_2da spells2da, TLKStore tlks, int rowNum, String scrollResRef) {
		int innateLevel = -1,
		         tlkRef = -1;
		
		// HACK - Skip non-PRC scrolls
		if(!scrollResRef.startsWith("prc_scr_"))
			return;
		
		try {
			innateLevel = Integer.parseInt(spells2da.getEntry("Innate", rowNum));
		} catch (NumberFormatException e) {
			LOGGER.debug("Non-number value in spells.2da Innate column on line " + rowNum + ": " + spells2da.getEntry("Innate", rowNum), e);
			return;
		}
		
		try {
			tlkRef = Integer.parseInt(spells2da.getEntry("Name", rowNum));
		} catch (NumberFormatException e) {
			LOGGER.debug("Non-number value in spells.2da Name column on line " + rowNum + ": " + spells2da.getEntry("Name", rowNum), e);
			return;
		}
		
		if(scrollResRefs.get(innateLevel) == null)
			scrollResRefs.put(innateLevel, new TreeMap<String, String>());
		
		scrollResRefs.get(innateLevel).put(tlks.get(tlkRef), scrollResRef);
	}

	/**
	 * Prints the use instructions for this program and kills execution.
	 */
	private static void readMe() {
		//                  0        1         2         3         4         5         6         7         8
		//					12345678901234567890123456789012345678901234567890123456789012345678901234567890
		System.out.println("Usage:\n"+
		                   "  java -jar prc.jar scrmrchgen 2dadir tlkdir | [--help]\n"+
		                   "\n"+
		                   "2dadir   Path to a directory containing des_crft_scroll.2da and spells.2da.\n" +
		                   "tlkdir   Path to a directory containing dialog.tlk and prc_consortium.tlk\n" +
						   "\n"+
		                   "--help      prints this info you are reading\n" +
		                   "\n" +
		                   "\n" +
		                   "Generates a merchant file - prc_scrolls.utm - based on the given scroll list\n" +
		                   "2da file. The merchant file will be written to current directory in Pspeed's\n" +
		                   "XML <-> Gff -xml format\n"
		                  );
		System.exit(0);
	}
}
