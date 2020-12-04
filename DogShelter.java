package assignment3;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class DogShelter implements Iterable<Dog> {
    public DogNode root;

    public DogShelter(Dog d) {
        this.root = new DogNode(d);
    }

    private DogShelter(DogNode dNode) {
        this.root = dNode;
    }


    // add a dog to the shelter
    public void shelter(Dog d) {
        if (root == null)
            root = new DogNode(d);
        else
            root = root.shelter(d);
    }

    // removes the dog who has been at the shelter the longest
    public Dog adopt() {
        if (root == null)
            return null;

        Dog d = root.d;
        root = root.adopt(d);
        return d;
    }

    // overload adopt to remove from the shelter a specific dog
    public void adopt(Dog d) {
        if (root != null)
            root = root.adopt(d);
    }


    // get the oldest dog in the shelter
    public Dog findOldest() {
        if (root == null)
            return null;

        return root.findOldest();
    }

    // get the youngest dog in the shelter
    public Dog findYoungest() {
        if (root == null)
            return null;

        return root.findYoungest();
    }

    // get dog with highest adoption priority with age within the range
    public Dog findDogToAdopt(int minAge, int maxAge) {
        return root.findDogToAdopt(minAge, maxAge);
    }

    // Returns the expected vet cost the shelter has to incur in the next numDays days
    public double budgetVetExpenses(int numDays) {
        if (root == null)
            return 0;

        return root.budgetVetExpenses(numDays);
    }

    // returns a list of list of Dogs. The dogs in the list at index 0 need to see the vet in the next week.
    // The dogs in the list at index i need to see the vet in i weeks.
    public ArrayList<ArrayList<Dog>> getVetSchedule() {
        if (root == null)
            return new ArrayList<ArrayList<Dog>>();

        return root.getVetSchedule();
    }


    public Iterator<Dog> iterator() {
        return new DogShelterIterator();
    }


    public class DogNode {
        public Dog d;
        public DogNode younger;
        public DogNode older;
        public DogNode parent;

        public DogNode(Dog d) {
            this.d = d;
            this.younger = null;
            this.older = null;
            this.parent = null;
        }
        //private method to add dog to BST
        private DogNode addToBST(Dog d) {
            if (this.d.getAge() > d.getAge()) {
                //checking if left child is null
                if(this.younger == null) {
                    //adding new dog node to existing tree
                    this.younger = new DogNode(d);
                    this.younger.parent = this;
                    return this.younger;
                }
                //if not empty, want to recurs call on child node
                //calling method on child
                else {
                    return this.younger.addToBST(d);
                }
            }
            else if (this.d.getAge() < d.getAge()) {
                if(this.older == null) {
                    //adding new dog node to existing tree
                    this.older = new DogNode(d);
                    this.older.parent = this;
                    return this.older;
                }
                //if not empty, want to recurs call on child node
                //calling method on child
                else {
                    return this.older.addToBST(d);
                }
            }
            else {
                return null;
            }

        }

        DogNode right (DogNode d,DogNode p){
            p.younger= d.older;
            if (d.older != null) {
                d.older.parent = d.parent;
            }
            d.older = p;
            return d;
        }

        private DogNode left (DogNode d, DogNode p){
            p.older= d.younger;
            if (d.younger != null) {
                d.younger.parent = p;
            }
            d.younger = p;
            return d;
        }

        public DogNode shelter(Dog d) {
            // Recursive function to insert a given key with a priority into Treap
            // add element as bst
            //dog added
            DogNode cur = addToBST(d);
            //dog added parent
            DogNode p = cur.parent;

            //if treap is more than one node
            while ((p != null)&&(d.getDaysAtTheShelter() > p.d.getDaysAtTheShelter())) {
                if (p.parent != null) {
                    //fix p.parent's link with it's children
                    //if parent is right child
                    if (p == p.parent.younger) {
                        p.parent.younger = cur;
                    }
                    //if parent is left child left child
                    if (p == p.parent.older) {
                        p.parent.older = cur;
                    }
                }
                cur.parent = p.parent;
                p.parent = cur;
                //left child
                if (p.younger == cur) {
                    // rotate right if heap property is violated
                    cur = right(cur,p);
                }
                // rotate left if heap property is violated
                else if (p.older == cur) {
                    cur = left(cur,p);
                }
                p = cur.parent;
            }
                if (p == null) {
                    return cur;
                }
            return this;
}
        private DogNode rightRotate(DogNode d, DogNode p) {
            if (p != null) {
                if (d.older != null) {
                    d.older.parent = p;
                    p.younger = d.older;
                }
                else {
                    p.younger = null;
                }
            }
            d.older = p;
            if (p.parent != null) {
                if (p.parent.older == p) {
                    p.parent.older = d;
                }
                else  {
                    p.parent.younger = d;
                }
            }
            d.parent = p.parent;
            p.parent = d;
            return d;
        }

        private DogNode leftRotate(DogNode d,  DogNode p) {
            if (p != null) {
                if (d.younger != null) {
                    d.younger.parent = p;
                    p.older = d.younger;
                }
                else {
                    p.older = null;
                }
            }
            d.younger = p;
            if (p.parent != null) {
                if (p.parent.older == p) {
                    p.parent.older = d;
                }
                else  {
                    p.parent.younger = d;
                }
            }
            d.parent = p.parent;
            p.parent = d;
            return d;
        }
        public DogNode adopt(Dog d) {
            DogNode cur = this;
            // if key is less than the root node, recur for left subtree
            if (d.getAge() < this.d.getAge()) {
                this.younger = this.younger.adopt(d);
            }
            // if key is more than the root node, recur for right subtree
            else if (d.getAge() > this.d.getAge()) {
                this.older = this.older.adopt(d);
            }
            // if key found
            else {
                // Case 1: node to be deleted has no children (it is a leaf node)
                if (this.younger == null && this.older == null) {
                    // deallocate the memory and update root to null
                    this.d = null;
                    return null;
                }
                // Case 2: node to be deleted has two children
                else if (this.younger != null && this.older != null) {
                    Dog oldD = cur.younger.findOldest();
                    cur.d = oldD;
                    //removes oldD
                    cur.younger.adopt(oldD);

                    while (((cur.younger != null && cur.younger.d.getDaysAtTheShelter() > cur.d.getDaysAtTheShelter())
                            || ((cur.older != null && cur.older.d.getDaysAtTheShelter() > cur.d.getDaysAtTheShelter())))) {

                        if (cur.younger == null && cur.older != null) {
                            leftRotate(cur.older,cur);

                        } else if (cur.older == null && cur.younger != null) {
                            rightRotate(cur.younger,cur);
                        } else {
                            if (cur.younger.d.getDaysAtTheShelter() > cur.older.d.getDaysAtTheShelter()) {
                                rightRotate(cur.younger,cur);
                            } else {
                                leftRotate(cur.older,cur);
                            }
                        }
                    }
                    if (cur.parent == null) {
                        return cur;
                    } else {
                        while (cur.parent != null) {
                           cur = cur.parent;
                        }
                        return cur;
                    }
            }
                // Case 3: node to be deleted has only one child
                else {
                    if (this.younger != null) {
                        this.younger.parent = this.parent;
                        if (this.parent != null) {
                            if (this.parent.younger == this) {
                                this.parent.younger = this.younger;
                            }
                        else {
                            this.parent.older = this.older;
                        }
                    }
                        return this.younger;
                    }
                    //right child
                    else{
                        this.older.parent = this.parent;
                        if (this.parent != null) {
                            if (this.parent.younger == this) {
                                this.parent.younger = this.younger;
                            } else {
                                this.parent.older = this.older;
                            }
                        }
                        return this.older;
                    }
                }
            }
            return this;
        }

            public Dog findOldest() {
                DogNode cur = this;
                while (cur.older != null) {
                    cur = cur.older;
                }
                return cur.d;
            }

            public Dog findYoungest () {
                DogNode cur = this;
                while (cur.younger != null) {
                    cur = cur.younger;
                }
                return cur.d;
            }

            public Dog findDogToAdopt ( int minAge, int maxAge){

            // if dogNode is in range return
                if (this.d != null && this.d.getAge() >= minAge && this.d.getAge() <= maxAge) {
                    return this.d;
                    //if dogNode is greater than range, recurse on younger
                } else if (this.d.getAge() > maxAge && this.younger != null) {
                    return this.younger.findDogToAdopt(minAge, maxAge);
                   // if dogNode is smaller than range, recurse on older
                } else if (this.d.getAge() < minAge && this.older != null) {
                    return this.older.findDogToAdopt(minAge, maxAge);
                }
                else{
                    return null;
                }
            }

            public double budgetVetExpenses ( int numDays){
            double totalCost = 0;
                    //traverse through the ENTIRE tree

                    //visit node
                    if (this.d.getDaysToNextVetAppointment() <= numDays) {
                            totalCost += this.d.getExpectedVetCost();
                        }
                        if (this.younger != null) {
                            totalCost += this.younger.budgetVetExpenses(numDays);
                        }
                        if (this.older != null) {
                            totalCost += this.older.budgetVetExpenses(numDays);
                        }


                return totalCost;
            }

            public ArrayList<ArrayList<Dog>> getVetSchedule () {
                ArrayList<ArrayList<Dog>> schedule = new ArrayList<ArrayList<Dog>>();

                DogShelter dogs = new DogShelter(this);

                for(Dog d: dogs){
                    int week = d.getDaysToNextVetAppointment()/7;
                    if(week>schedule.size()-1){
                        int i= week;
                        while(i>schedule.size()-1)
                        schedule.add(new ArrayList<Dog>());
                        i-=1;
                    }
                    schedule.get(week).add(d);
                }
                return schedule; // DON'T FORGET TO MODIFY THE RETURN IF NEED BE
            }

            public String toString () {
                String result = this.d.toString() + "\n";
                if (this.younger != null) {
                    result += "younger than " + this.d.toString() + " :\n";
                    result += this.younger.toString();
                }
                if (this.older != null) {
                    result += "older than " + this.d.toString() + " :\n";
                    result += this.older.toString();
                }
			/*if (this.parent != null) {
				result += "parent of " + this.d.toString() + " :\n";
				result += this.parent.d.toString() +"\n";
			}*/
                return result;
            }

        }


        private class DogShelterIterator implements Iterator<Dog> {
            ArrayList<Dog> list = null;
            Iterator iterator = null;


            private void build(DogNode root) {
                if (root != null) {
                    build(root.younger);
                    list.add(root.d);
                    build(root.older);
                }
            }

            private DogShelterIterator() {
                list = new ArrayList<Dog>();
                build(root);
                iterator = list.iterator();
            }

            @Override
            public boolean hasNext() {
                return iterator.hasNext();
            }

            public Dog next() {
                if(! this.hasNext()) throw new NoSuchElementException();
                return (Dog) iterator.next();
            }
        }


    }


