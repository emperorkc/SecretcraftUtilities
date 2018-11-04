package de.secretcraft.main;

import org.bukkit.entity.Player;

public class Output {
	static String prefix = UtilitiesConfig.getPrefix();
	public static void Err(Player p, String nr) {
		switch (nr) {
		case "perm":

			p.sendMessage(prefix+" �cDu hast hierzu keine Rechte.");
			break;
		case "nBuy01":
			p.sendMessage(prefix+" �cDiese Flag darf derzeit nicht f�r diese Region gekauft werden.");
			break;
		case "cUse1":
			p.sendMessage(prefix+" �cKorrekte Nutzung: /availableflags");
			break;
		case "cUse2":
			p.sendMessage(
					prefix+" �cKorrekte Nutzung: /flag kaufen <regionsname> <flagname> <ein/aus/text>");
			break;
		case "cUse3":
			p.sendMessage(prefix+" �cKorrekte Nutzung: /flag add <flagname> <preis>");
			break;
		case "cUse4":
			p.sendMessage(prefix+" �cKorrekte Nutzung: /flag remove <flagname>");
			break;
		case "cUse5":
			p.sendMessage(prefix+" �cKorrekte Nutzung: /flag preis <flagname>");
			break;
		case "cUse6":
			p.sendMessage(prefix+" �cKorrekte Nutzung: /flag info <flagname>");
			break;
		case "cUse7":
			p.sendMessage(prefix+" �cKorrekte Nutzung: /flag change <flagname> <neuerpreis>");
			break;
		case "cUse8":
			p.sendMessage(prefix+" �cKorrekte Nutzung: /flag del <region> <flagname>");
			break;
		case "cUse9":
			p.sendMessage(prefix+" �cKorrekte Nutzung: /flag forbiddenflags <flagname>");
			break;
		case "cUse10":
			p.sendMessage(
					prefix+" �cKorrekte Nutzung: /flag <help/kaufen/add/remove/preis/info/del/forbid/allow/forbiddenflags>");
			break;
		case "cUse11":
			p.sendMessage(prefix+" �cKorrekte Nutzung: /flag <kaufen/preis/info/help>");
			break;
		case "cUse12":
			p.sendMessage(prefix+" �cKorrekte Nutzung: /flags");
			break;
		case "cUse13":
			p.sendMessage(prefix+" �cKorrekte Nutzung: /flag forbid <region> <flag>");
			break;
		case "cUse14":
			p.sendMessage(prefix+" �cKorrekte Nutzung: /flag allow <region> <flag>");
			break;
		case "aRegi":
			p.sendMessage(prefix+" �cDie Flag ist bereits registriert.");
			break;
		case "wVal":
			p.sendMessage(prefix+" �cDie angegebene Value ist f�r diese Flag nicht kompatibel");
			break;
		case "wFlag01":
			p.sendMessage(prefix+" �cDiese Flag ist ung�ltig");
			break;
		case "sFlag01":
			p.sendMessage(prefix+" �cDie Flag ist bereits mit genannten Argumenten eingetragen");
			break;
		case "invFlag":
			p.sendMessage("�cDiese Flag wird nicht vom Plugin unterst�tzt. Nutze /availableflags");
			break;
		case "nReg":
			p.sendMessage(prefix+" �cDie Flag ist nicht registriert");
			break;
		case "aForb":
			p.sendMessage(

					prefix+" �cDiese Flag ist bereits f�r diese Region Verboten.");
			break;

		case "nForbid":
			p.sendMessage(prefix+" �cDiese Flag wurde f�r keine Region verboten.");
			break;

		case "nMoney":
			p.sendMessage(prefix+" �cDu hast nicht gen�gend Geld um die Flag zu kaufen.");
			break;
		

			
			
		case "cUse15":
			p.sendMessage(prefix+" �cKorrekte Nutzung: /tiere <Region> bzw. /tiere radius <Radius>");
			break;
		case "cUse16":
			p.sendMessage(prefix+" �cKorrekte Nutzung: /tiere radius <Radius>");
			break;
		case "cUse17":
			p.sendMessage(prefix+" �cDer Radius darf maximal 200 Bl�cke betragen.");
			break;
		case "uReg":
			p.sendMessage(prefix+" �cDiese Region existiert nicht.");
			break;
		case "aExist":
			p.sendMessage(prefix+" �cDiese Region existiert bereits. L�sche sie mit /gs del <name>, sollte sie deine Region sein.");
			break;
		case "nPerm":
			p.sendMessage(prefix+" �cDu hast hierzu keine Rechte.");
			break;
		case "nExist":
			p.sendMessage(prefix+" �cDiese Region existiert nicht.");
			break;
		case "nOwner":
			p.sendMessage(prefix+" �cDu bist nicht der Besitzer dieser Region.");
			break;
		case "nName":
			p.sendMessage(
					prefix+" �cLege zuerst einen Namen f�r deine Region fest -->/gs <name>.");
			break;
		case "aExp":
			p.sendMessage(prefix+" �cDu hast die Region bereits Vertikal expandiert!");
			break;
		case "eRedo":
			p.sendMessage(prefix+" �cEs gibt nichts zur�cksetzen.");

			break;
		case "cUse18":
			p.sendMessage(
					prefix+" �cKorrekte Nutzung: /gs <name>/info/vert/up/down/redo/del. Mehr dazu: /gs info");
			break;
		case "cUse19":
			p.sendMessage(prefix+" �cKorrekte Nutzung: /gs up <Anzahl>");
			break;
		case "cUse20":
			p.sendMessage(prefix+" �cKorrekte Nutzung: /gs down <Anzahl>");
			break;
		case "cUse21":
			p.sendMessage(prefix+" �cKorrekte Nutzung: /gs del <region>");
			break;

		case "aName":
			p.sendMessage(prefix+" �cDu hast bereits einen Namen f�r die Region gew�hlt.");
			break;
		case "nSel":
			p.sendMessage(prefix+" �cW�hle zuerst eine Region aus.");
			break;
		}
		}

	

	public static void suc(Player p, String nr) {
		switch (nr) {
		case "discord":
			p.sendMessage(prefix+" �eLink zum Discord:�6 https://bit.ly/2LQtS2t");
			break;
		case "vote1":
			p.sendMessage(prefix+" �eVote auf:�6 https://bit.ly/2CctiIh");
			break;
		case "vote2":
			p.sendMessage(prefix+" �eVote auf:�6 https://bit.ly/2uYWr3k");
			break;
		case "vote3":
			p.sendMessage(prefix+" �eVote auf:�6 https://bit.ly/2AkA39S");
			break;
		case "vote4":
			p.sendMessage(prefix+" �eVote auf:�6 https://bit.ly/2NSZnWt");
			break;
		case "vote5":
			p.sendMessage(prefix+" �eVote auf:�6 https://bit.ly/2mLTSxg");
			break;
		case "vote6":
			p.sendMessage(prefix+" �eVote auf:�6 https://bit.ly/2Bbn1f7");
			break;
		case "vote":
			p.sendMessage(prefix+" �eVote auf:�6 https://bit.ly/2CctiIh");p.sendMessage("");p.sendMessage(prefix+" �eVote auf:�6 https://bit.ly/2uYWr3k");p.sendMessage("");
			p.sendMessage(prefix+" �eVote auf:�6 https://bit.ly/2AkA39S");p.sendMessage("");p.sendMessage(prefix+" �eVote auf:�6 https://bit.ly/2NSZnWt");p.sendMessage("");
			p.sendMessage(prefix+" �eVote auf:�6 https://bit.ly/2mLTSxg");p.sendMessage("");p.sendMessage(prefix+" �eVote auf:�6 https://bit.ly/2Bbn1f7");
			break;
		case "tutorial":
			p.sendMessage(prefix+" �eLink zum Tutorial:�6 https://secretcraft.de/von-anfang-bis-ende/");
			break;
		case "twitch":
			p.sendMessage(prefix+" �eLink zum Twitch-Kanal:�6 https://twitch.tv/serial1990");
			break;
		case "help":
			p.sendMessage(prefix+" �eLink zum den Befehlen:�6 https://secretcraft.de/befehle/");
			break;
		case "forum":
			p.sendMessage(prefix+" �eLink zum Forum:�6 https://bit.ly/2Mm6q9T");
			break;
		case "regeln":
			p.sendMessage(prefix+" �eLink zu den Regeln:�6 https://secretcraft.de/regeln/");
			break;
		case "youtube":
			p.sendMessage(prefix+" �eLink zum Kanal:�6 https://bit.ly/2vgNWAL");
			break;
		case "info1":
			p.sendMessage(
					prefix+" �2Die Flag �4�lPVP�2 erlaubt es dir PVP auf deiner Region einzuschalten oder auszuschalten. In St�dten standardm��ig �4�laus");
			break;
		case "info2":
			p.sendMessage(
					prefix+" �2Die Flag �4�lGREETING�2 erlaubt es dir eine Begr��ung im Chat erscheinen zu lassen, wenn ein Spieler deine Region betritt.");
			break;
		case "info3":
			p.sendMessage(
					prefix+" �2Die Flag �4�lFAREWELL�2 erlaubt es dir einen Abschiedsgru� im Chat erscheinen zu lassen, wenn ein Spieler deine Region verl�sst.");
			break;
		case "info4":
			p.sendMessage(
					prefix+" �2Die Flag �4�lSNOW-FALL�2 erlaubt es dir den Scheefall in deiner Region einzuschalten oder auszuschalten. In St�dten standardm��ig �4�lein");
			break;
		case "info5":
			p.sendMessage(
					prefix+" �2Die Flag �4�lCREEPER-EXPLOSION�2 erlaubt es dir Explosionen von Creepern in deiner Region einzuschalten oder auszuschalten. In St�dten standardm��ig �4�laus");
			break;
		case "info6":
			p.sendMessage(
					prefix+" �2Die Flag �4�lOTHER-EXPLOSION�2 erlaubt es dir Explosionen in deiner Region einzuschalten oder auszuschalten.In St�dten standardm��ig �4�laus");
			break;
		case "info7":
			p.sendMessage(
					prefix+" �2Die Flag �4�lTNT�2 erlaubt es dir TNT in deiner Region einzuschalten oder auszuschalten. In St�dten standardm��ig �4�lein");
			break;
		case "info8":
			p.sendMessage(
					prefix+" �2Die Flag �4�lLIGHTER�2 erlaubt es dir die Nutzung von Feuerzeugen in deiner Region einzuschalten oder auszuschalten. In St�dten standardm��ig �4�lein");
			break;
		case "info9":
			p.sendMessage(
					prefix+" �2Die Flag �4�lICE-MELT�2 erlaubt es dir das schmelzen von Eis in deiner Region einzuschalten oder auszuschalten. In St�dten standardm��ig �4�lein");
			break;
		case "info10":
			p.sendMessage(
					prefix+" �2Die Flag �4�lICE-FORM�2 erlaubt es dir das Formen von Eis in deiner Region einzuschalten oder auszuschalten. In St�dten standardm��ig �4�lein");
			break;
		case "info11":
			p.sendMessage(
					prefix+" �2Die Flag �4�lSNOW-MELT�2 erlaubt es dir das schmelzen von Schnee in deiner Region einzuschalten oder auszuschalten. In St�dten standardm��ig �4�laus");
			break;
		case "info12":
			p.sendMessage(
					prefix+" �2Die Flag �4�lBUILD�2 erlaubt es dir f�r alle anderen Spieler die F�higkeit des Bauens in deiner Region einzuschalten oder auszuschalten. In St�dten standardm��ig �4�laus");
			break;
		case "info13":
			p.sendMessage(
					prefix+" �2Die Flag �4�lMOB-SPAWNING�2 erlaubt es dir das Spawnen von Mobs in deiner Region einzuschalten oder auszuschalten. In St�dten standardm��ig �4�laus");
			break;
		case "info14":
			p.sendMessage(
					prefix+" �2Die Flag �4�lENDERPEARL�2 erlaubt es dir das Nutzen von Enderperlen in deiner Region einzuschalten oder auszuschalten. In St�dten standardm��ig �4�lein");
			break;
		case "info15":
			p.sendMessage(
					prefix+" �2Die Flag �4�lHOSTILEMOBSPAWN�2 erlaubt es dir das Spawnen von Monstern einzuschalten oder auszuschalten. In St�dten standardm��ig �4�laus");
			break;

		case "sBuy01":

			p.sendMessage(prefix+" �2Flag erfolgreich gekauft!");
			break;
		case "sAdd01":

			p.sendMessage(prefix+" �2Flag erfolgreich hinzugef�gt!");
			break;
		case "sRem":
			p.sendMessage(prefix+" �2Flag erfolgreich entfernt.");
			break;
		case "supPl":
			p.sendMessage(
					prefix+" �bDie folgenden Flags werden vom Plugin unterst�tzt: �2snow-fall, pvp, build, mob-spawning, creeper-explosion, enderpearl, other-explosion, tnt, lighter, snow-melt, ice-form, ice-melt, greeting, farewell, hostilemobspawn");
			break;
		case "sDeny":
			p.sendMessage(prefix+" �2Flag erfolgreich verboten!");
			break;
		case "sAllow":
			p.sendMessage(prefix+" �2Die Flag wurde erfolgreich f�r die genannte Region erlaubt.");
			break;
		case "aAllow":
			p.sendMessage(prefix+" �cDiese Flag ist f�r diese Region bereits erlaubt.");
			break;
		case "helpAdmin":
			p.sendMessage(
					prefix+" �2/flag kaufen <region> <flag> <ein/aus/text> �3- Kaufe eine bestimmte Flag");
			p.sendMessage(prefix+" �2/flag info <flag> �3- Sehe Info zur Flag ein");
			p.sendMessage(prefix+" �2/flag preis <flag> �3- Sehe Preis zur Flag ein");
			p.sendMessage(prefix+" �2/flags �3- Sehe verf�gbare Flags");

			p.sendMessage(prefix+" �2/flag add <flag> <preis> �3- Schalte Flag zum Verkauf frei");
			p.sendMessage(prefix+" �2/flag remove <flag> �3- Deaktiviere Verkauf der Flag");
			p.sendMessage(prefix+" �2/flag del <region> <flag> �3- L�sche Flag aus einer Region");
			p.sendMessage(prefix+" �2/flag change <flag> <neuerpreis> �3- �ndere den Preis der Flag");
			p.sendMessage(
					prefix+" �2/flag forbid <region> <flag> �3- Verbiete eine Flag f�r eine Region");
			p.sendMessage(
					prefix+" �2/flag allow <region> <flag> �3- Erlaube eine Flag f�r eine Region");
			p.sendMessage(
					prefix+" �2/flag forbiddenflags <flag> �3- Sehe verbotene Regionen der Flag ein");

			p.sendMessage(prefix+" �2/availableflags �3- Sehe vom Plugin unterst�tzte Flags ein");
			break;

		case "helpUser":
			p.sendMessage(
					prefix+" �2/flag kaufen <region> <flag> <ein/aus/text> �3- Kaufe eine bestimmte Flag");
			p.sendMessage(prefix+" �2/flag info <flag> �3- Sehe Info zur Flag ein");
			p.sendMessage(prefix+" �2/flag preis <flag> �3- Sehe Preis zur Flag ein");
			p.sendMessage(prefix+" �2/flags �3- Sehe verf�gbare Flags");
			break;
		case "forbFlags":
			p.sendMessage(
					prefix+" �2Um die verboteten Flags einzusehen, schaue bitte in der Config nach. Aufrufen per Command ist nicht m�glich. ");
			break;
		
		case "sDel":
			p.sendMessage(prefix+" �2Du hast die Region erfolgreich gel�scht.");
			break;
		case "sVert":
			p.sendMessage(
					prefix+" �2Du hast die Region erfolgreich �3Vertikal�2 erweitert. Nun best�tige deine Auswahl mit /gs confirm");
			break;
		case "info":
			p.sendMessage(
					prefix+" �2Solltest du mitten in deiner Regionskonfiguration einen Fehler machen, benutze /gs redo. Dann fange wieder beim Namen an.");
			p.sendMessage(prefix+" �41.�2 Markiere deine Region mit der Worldedit-Axt:");
			p.sendMessage(prefix+" �b//wand");
			p.sendMessage(prefix+" �42.�2 W�hle einen Namen f�r deine Region: ");
			p.sendMessage(prefix+" �b/gs <name>");
			p.sendMessage(prefix+" �43.�2 Erweitere deine Region:");
			p.sendMessage(prefix+" �b/gs vert�2 - Bedrock bis Himmel");
			p.sendMessage(prefix+" �b/gs up <Anzahl>�2 - Erweitere nach oben");
			p.sendMessage(prefix+" �b/gs down <Anzahl>�2 - Erweitere nach unten");
			p.sendMessage(prefix+" �44.�2 Sobald du deine Auswahl getroffen hast:");
			p.sendMessage(prefix+" �b/gs confirm");
			break;
		case "sRedo":
			p.sendMessage(
					prefix+" �2Mit dem Einrichten des Grundst�cks kann nun erneut begonnen werden.");
			break;
		case "nowNext1":
			p.sendMessage(
					prefix+" �2Name der Region erfolgreich gesetzt. Expandiere nun deine Region (�4/gs info�2) oder best�tige deine Wahl mit �4/gs confirm ");

			break;
		}

	}

}
