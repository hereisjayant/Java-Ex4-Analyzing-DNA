package dna;
// Use the test cases to guide you.

import java.util.ArrayList;
import java.util.Collections;
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
        if (!valid(nucleo_seq)) {
            throw new IllegalArgumentException("Invalid DNA sequence");
        }
        this.nucleo_seq = nucleo_seq;
    }

    public boolean isProtein() {
        ArrayList<String> nucleotide_seq = new ArrayList<>(codonList());
        boolean protein = false;
        //test 1
        protein = nucleotide_seq.get(0).equals("ATG");
        String last_codon = nucleotide_seq.get(nucleotide_seq.size() - 1);
        //test 2
        protein = last_codon.equals("TAA") || last_codon.equals("TAG") || last_codon.equals("TGA");
        //test 3
        protein = nucleotide_seq.size() >= 5;
        //test 4
        double C_mass = cytosine_mass * nucleotideCount('C');
        double G_mass = guanine_mass * nucleotideCount('G');
        double mass_fraction = (Math.round((C_mass + G_mass) * 10) / 10.0) / totalMass();
        protein = mass_fraction >= 0.30;

        return protein;
    }

    /**
     * @return the total mass of the DNA in gms/mol rounded off to one digit past decimal point
     */
    public double totalMass() {
        char[] sequence = nucleo_seq.toCharArray();
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

    public String sequence() {
        return nucleo_seq;
    }

    public void mutateCodon(String originalCodon, String newCodon) {
        //checks if valid:
        char[] origC = originalCodon.toCharArray();
        char[] newC = newCodon.toCharArray();
        boolean validOLD = false;
        boolean validNEW = false;
        for (char element : origC) {
            validOLD = element == 'A' || element == 'C' || element == 'G' || element == 'T';
            if (!validOLD) {
                break;
            }
        }
        for (char element : newC) {
            validNEW = element == 'A' || element == 'C' || element == 'G' || element == 'T';
            if (!validNEW) {
                break;
            }
        }
        boolean valid = validNEW && validOLD;


        if (valid) {
            ArrayList<String> list_of_codons = new ArrayList<>(codonList());

            Collections.replaceAll(list_of_codons, originalCodon, newCodon);

            StringBuilder new_seq = new StringBuilder("");
            for (String element : list_of_codons) {
                new_seq.append(element);
            }
            this.nucleo_seq = new_seq.toString();
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
        char[] sequence = nucleo_seq.toCharArray();
        StringBuilder nucleotide = new StringBuilder("");
        HashSet<String> codons = new HashSet<>();
        for (char element : sequence) {
            if (element == 'A' || element == 'C' || element == 'G' || element == 'T') {
                nucleotide.append(element);
            }
            if (nucleotide.length() == 3) {
                codons.add(nucleotide.toString());
                nucleotide = new StringBuilder("");
            }
        }
        return codons;
    }

    private ArrayList<String> codonList() {
        char[] sequence = nucleo_seq.toCharArray();
        StringBuilder nucleotide = new StringBuilder("");
        ArrayList<String> codons = new ArrayList<>();
        for (char element : sequence) {
            if (element == 'A' || element == 'C' || element == 'G' || element == 'T') {
                nucleotide.append(element);
            }
            if (nucleotide.length() == 3) {
                codons.add(nucleotide.toString());
                nucleotide = new StringBuilder("");
            }
        }
        return codons;
    }

    private boolean valid(String dna_sequence) {
        char[] sequence = dna_sequence.toCharArray();
        int no_of_nucleotides = 0;
        for (char element : sequence) {
            if (element == 'A' || element == 'C' || element == 'G' || element == 'T') {
                no_of_nucleotides++;
            }
        }
        if (no_of_nucleotides % 3 == 0) {
            return true;
        } else {
            return false;
        }
    }
}