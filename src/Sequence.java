import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * Integer sequence generators. A sequence is defined by its initial values, and a depth. The initial values represent the first values returned by the sequence. The depth is how many previous items are added together to calculate the next item in the sequence, after all initial values have been exhausted.
 * Examples:
 *
 * Sequence({4, 9, 12}, 0) produces [4, 9, 12, 0, 0, 0, 0, ...]
 * Sequence({1}, 1) produces [1, 1, 1, 1, 1, 1, ...]
 * Sequence({3, 8}, 1) produces [3, 8, 8, 8, 8, 8, 8, ...]
 * Sequence({2, 4}, 2) produces [2, 4, 6, 10, 16, 26, 42, 68, ...]
 * Sequence({2, 4, 6, 8}, 3) produces [2, 4, 6, 8, 18, 32, 58, 108, ...]
 * The behavior of sequences for which the depth is greater than number of the initial values is unspecified.
 *
 * @author api Dr.Jody
 * @author implementation Kendra Lamb
 * @version 2.0.1
 */
public class Sequence {

    /**
     * Depth of the the number of historical values used in calculation
     */
    private int depth = 0;

    /**
     * Integer sequence array holding intial values of the sequence
     */
    private List<Integer> sequence;

    /**
     * Holds the current position in the sequence.
     */
    private int index;

    /**
     * Constructs an arbitrary sequence.
     */
    public Sequence() {
        Random random = new Random();
        this.depth = random.nextInt(5);
        this.sequence = new ArrayList<>();
        this.index = 0;

        for(int i = 0; i < depth; i++) {
            this.sequence.add(random.nextInt());
        }
    }

    /**
     * Constructs a specified sequence.
     * @param init the initial values of the sequence
     * @param depth the number of historical values used in calculation
     */
    public Sequence(Integer[] init, int depth) {
        this.sequence = new ArrayList<>();
        if(init == null) {
            System.out.println("Init is null, setting to an empty array.");
            init = new Integer[]{};
        }

        List<Integer> initList = Arrays.asList(init);
        if(initList.size() > 0) {
            this.sequence.addAll(initList);
        }

        if(depth < 0) {
            System.out.println("Depth is a negative number," +
                    " cannot compute the next number in the sequence with negative numbers.");
            System.exit(-1);
        }

        this.depth = depth;
        this.index = 0;
    }

    /**
     * Accesses the next number in the sequence.
     * @return the next number in the sequence
     */
    public Integer next() {
        int nextInt = 0;

        if(this.sequence.size() == 0) {
            nextInt = 0;
            addNewInteger(nextInt);
        } else if(this.sequence.size() <= index) { //Off the sequence array
            if(depth == 0) {
                nextInt = 0;
            } else {
                nextInt = calculateNext();
            }
            addNewInteger(nextInt);
        } else if(this.sequence.size() > index) {
            if(depth > index) {
                nextInt = this.sequence.get(index);
            } else if(depth == index) {
                if(this.sequence.get(index) != null) {
                    nextInt = this.sequence.get(index);
                } else {
                    nextInt = calculateNext();
                    addNewInteger(nextInt);
                }
            } else {
                nextInt = this.sequence.get(index);
            }

        } else {
            nextInt = -1;
        }

        index++;
        System.out.println("Sequence: " + this.sequence.toString());
        return nextInt;
    }

    private void addNewInteger(int nextInt) {
        this.sequence.add(nextInt);
    }

    private int calculateNext() {
        int nextInt = 0;
        for(int i = this.sequence.size() - depth; i < this.sequence.size(); i++) {
            nextInt += this.sequence.get(i);
        }
        return nextInt;
    }
}
