import imageToolBox.AbstractFilter;


public class Nachbarschaften_PK extends AbstractFilter {
	
	/*
	 * Jedes Voxel hat...
	 * - 6 Nachbarn, mit denen es eine Flaeche teilt (nenne diese Typ I)
	 * - 12 Nachbarn, mit denen es keine Flaeche, aber eine Kante teilt (Typ II)
	 * - 8 Nachbarn, mit denen es keine Kante, aber eine Ecke teilt (Typ III)
	 * Das sind insgesamt 26 Nachbarn. Theoretisch moeglich waeren damit 2^26 verschiedene Nachbarschaftsdefinitionen 
	 * (die ich an dieser Stelle nicht alle aufzaehlen werde, da dies den Rahmen der Aufgabe sprengen wuerde), 
	 * diese sind aber nicht alle gleich sinnvoll. Einige fuer die Praxis nuetzliche Definitionen koennten sein:
	 * - nur Typ I: 6 Nachbarn
	 * - Typ I und Typ II: 18 Nachbarn
	 * - Typ I, II und III: 26 Nachbarn
	 */

}
