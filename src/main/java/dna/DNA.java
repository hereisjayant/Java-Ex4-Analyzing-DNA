package dna;
// Use the test cases to guide you.

import java.util.HashMap;
import java.util.HashSet;

public class DNA {
    private final double adenine_mass = 135.128;
    private final double cytosine_mass = 111.103;
    private final double guanine_mass = 151.128;
    private final double thymine_mass = 125.107;
    private final double junk_mass = 100.000;
    HashMap<Character, Integer> nucleo_map = new HashMap<>();
    private String nucleo_seq;

    public DNA(String nucleo_seq) {
        this.nucleo_seq = nucleo_seq;
    }

    public boolean isProtein() {

        return true;
    }

    /**
     * @return the total mass of the DNA in gms/mol rounded off to one digit past decimal point
     */
    public double totalMass() {
        char sequence[] = nucleo_seq.toCharArray();
        double total_mass = 0.0;
        for (char element : sequence) {
            if (element == 'A') {
                total_mass = total_mass + adenine_mass;
            } else if (element == 'C') {
                total_mass = total_mass + cytosine_mass;
            } else if (element == 'G') {
                total_mass = total_mass + guanine_mass;
            } else if (element == 'T') {
                total_mass = total_mass + thymine_mass;
            } else {
                total_mass = total_mass + junk_mass;
            }
        }
        return Math.round(total_mass * 10.0) / 10.0;
    }

    private void makeMap() {
        nucleo_map.put('A', 0);
        nucleo_map.put('C', 0);
        nucleo_map.put('G', 0);
        nucleo_map.put('T', 0);
        nucleo_map.put('J', 0);

        char sequence[] = nucleo_seq.toCharArray();
        for (char element : sequence) {
            if (element == 'A' || element == 'C' || element == 'G' || element == 'T') {
                nucleo_map.put(element, nucleo_map.get(element) + 1);
            } else {
                nucleo_map.put('J', nucleo_map.get('J') + 1);
            }
        }
    }

    /**
     * @param c
     * @return returns the number of times c occurs in the nucleotide
     */
    public int nucleotideCount(char c) {
        makeMap();
        if (c == 'A' || c == 'C' || c == 'G' || c == 'T') {
            return nucleo_map.get(c);
        } else {
            return 0;
        }
    }

    public HashSet<String> codonSet() {
        char sequence[] = nucleo_seq.toCharArray();
        StringBuilder nucleotide = new StringBuilder("");
        int nucleotide_number = 0;
        for (char element : sequence) {
            if (element == 'A' || element == 'C' || element == 'G' || element == 'T') {
                nucleotide.append(element);
            }
        }

    }