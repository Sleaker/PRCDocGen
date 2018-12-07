package prc;

import prc.autodoc.Spinner;

public class AppMain {

	/** Version number for public releases. Raise by one whenever doing a release. */
	private static final int releaseNum = 1;

	/** A boolean determining whether to spam the user with progress information */
	public static boolean verbose = true;

	/** A decorative spinner to look at while the program is loading big files */
	public static Spinner spinner = new Spinner();

	/**
	 * Ooh, a main method!
	 *
	 * @param args arguments, surprisingly enough
	 *
	 * @throws Throwable everything received from the classes called is passed on
	 */
	public static void main(String[] args) throws Throwable{
		if(args.length == 0 || args[0].equals("--help"))
			readMe();

		String app = args[0];
		String[] params = new String[args.length - 1];
		System.arraycopy(args, 1, params, 0, params.length);

		if(app.equals("manual")) {
			prc.autodoc.Autodoc.main(params);
		}
		else if(app.equals("2da")) {
			prc.autodoc.Data_2da.main(params);
		}
		else if(app.equals("codegen")) {
			prc.utils.CodeGen.main(params);
		}
		else if(app.equals("radials")) {
			prc.utils.Radials.main(params);
		}
		else if(app.equals("lssubrad")) {
			prc.utils.ListSubradials.main(params);
		}
		else if(app.equals("dupsubrad")) {
			prc.utils.DuplicateSubradials.main(params);
		}
		else if(app.equals("makedep")) {
			prc.makedep.Main.main(params);
		}
		else if(app.equals("upclsfeat")) {
			prc.utils.AllClassFeatUpdater.main(params);
		}
		else if(app.equals("lsentries")) {
			prc.utils.List2daEntries.main(params);
		}
		else if(app.equals("dupentries")) {
			prc.utils.Duplicate2daEntryDetector.main(params);
		}
		else if(app.equals("2datosql")) {
			prc.utils.SQLMaker.main(params);
		}
		else if(app.equals("spellbookmaker")) {
			prc.utils.SpellbookMaker.main(params);
		}
		else if(app.equals("amsspellbookmaker")) {
			prc.utils.AMSSpellbookMaker.main(params);
		}
		else if(app.equals("itempropmaker")) {
			prc.utils.ItempropMaker.main(params);
		}
		else if(app.equals("letoxml")) {
			prc.utils.LetoListsGenerator.main(params);
		}
		else if(app.equals("prec2dagen")) {
			prc.utils.Precache2daGen.main(params);
		}
		else if(app.equals("scrmrchgen")) {
			prc.utils.ScrollMerchantGen.main(params);
		}
		else if(app.equals("npcevol")) {
			prc.utils.NPCEvolve.main(params);
		}
		else if(app.equals("2damerge")) {
			prc.utils.Data2daMerge.main(params);
		}
		else if(app.equals("blank2da")) {
			prc.utils.Blank2daRows.main(params);
		}
		else if(app.equals("validator")) {
			prc.utils.Validator.main(params);
		}
		else if(app.equals("updatedescrft")) {
			prc.utils.UpdateDes.main(params);
		}
		else if(app.equals("scrollgen")) {
			prc.utils.ScrollGen.main(params);
		}
		else if(app.equals("buildscrhack")) {
			prc.utils.BuildScrollHack.main(params);
		}
		else {
			System.out.println("Unknown class: " + app);
			readMe();
		}
	}


	/**
	 * Prints the use instructions for this program and kills execution.
	 */
	private static void readMe(){
		//                  0        1         2         3         4         5         6         7         8
		//                  12345678901234567890123456789012345678901234567890123456789012345678901234567890
		System.out.println("Usage:\n"+
		                   "  java -jar prc.jar [--help] | tool [parameters]\n"+
		                   "\n"+
		                   "tool    name of the tool to call. possible values:\n"+
		                   "         2da            - Either verifies a single 2da file or compares two\n"+
		                   "         2damerge       - Merges 2 directories of 2da files, and outputs to a third\n" +
		                   "         blank2da       - Blanks rows in a 2da\n" +
		                   "         codegen        - Autogenerates scripts (or other files)\n"+
		                   "         dupentries     - Seeks for duplicate entries in the given columns\n" +
		                   "                          of a given 2da\n"+
		                   "         dupsubrad      - Seeks through spells.2da and prints lines\n"+
		                   "                          containing duplicate subradial values\n"+
		                   "         letoxml        - Creates Leto XML lists from 2da and TLK\n" +
		                   "         lsentries      - Lists the unique entries in given columns of a\n" +
		                   "                          given 2da\n" +
		                   "         lssubrad       - Lists subradial IDs used in spells.2da\n" +
		                   "         makedep        - Builds include dependency lists\n" +
		                   "         radials        - Generates subradial FeatID values\n"+
		                   "\n" +
		                   "        The following tools are also available, but almost certainly too\n" +
		                   "        specialized for general use:\n" +
		                   "         2datosql       - Creates a SQL file from 2das\n" +
		                   "         itempropmaker  - Creates the itemproperty cache item templates\n" +
		                   "         manual         - Generates the manual\n"+
		                   "         npcevol        - Alters packages based on logfile scores\n" +
		                   "         upclsfeat      - Updates base cls_feat_*.2da based on given templates\n" +
		                   "         prec2dagen     - Creates a 2da file that lists spells/feat.2da rows\n" +
		                   "                          that should be precached\n" +
		                   "         scrmrchgen     - Creates scroll merchant based on des_crft_scroll.2da\n" +
		                   "         spellbookmaker - Creates and/or updates the new spellbooks data\n" +
		                   "         amsspellbookmaker - Creates and/or updates the new AMS spellbooks data\n" +
		                   "         validator      - Performs a bunch of 2da integrity tests\n" +
		                   "         updatedescrft  - Updates des_crft_*.2da based on spells.2da\n" +
		                   "         scrollgen      - Create spell scrolls based on (iprp_)spells.2da\n" +
		                   "\n"+
		                   "parameters  a list of parameters passed to the tool called\n"+
		                   "\n"+
		                   "--help      prints this info you are reading\n" +
		                   "\n" +
		                   "\n" +
		                   "Release number: " + releaseNum + "\n"
		                  );
		System.exit(0);
	}
}
