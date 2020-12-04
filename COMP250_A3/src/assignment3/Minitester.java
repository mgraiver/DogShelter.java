package assignment3;

import java.util.ArrayList;

public class Minitester {
    public static void main(String[] args) {
       testShelter1();
       testShelter2();
        testShelter3();
        testShelter4();
        testAdopt1();
        testAdopt1a();
        testAdopt1b();
        testAdopt2();
        testAdopt3();
        testFindOldest1();
        testFindYoungest1();
        testFindDogToAdopt1();
        testBudgetVetExpenses1();
        testGetVetSchedule1();
        testGetVetSchedule2();
        testDogShelterIterator1();
        testDogShelterIterator2();
        testDogShelterIterator3();
    }


    private static void testShelter1() {
        Dog R = new Dog("Rex", 8, 100, 5, 50.0);
        Dog S = new Dog("Stella", 5, 50, 2, 250.0);
        Dog P = new Dog("Poldo", 10, 60, 1, 35.0);

        DogShelter test = new DogShelter(R);

        System.out.print("Testing shelter() #1 [0 tree rotation]... ");
        test.shelter(S);
        test.shelter(P);

        StringBuilder errorBuilder = new StringBuilder();
        boolean checkDogs = test.root.d == R && test.root.younger.d == S && test.root.older.d == P;
        if (!checkDogs) {
            errorBuilder.append("Dogs are not assigned correctly. ");
        }
        boolean checkParents = test.root.younger.parent.d == R && test.root.older.parent.d == R;
        if (!checkParents) {
            errorBuilder.append("Parent pointers are not assigned correctly. ");
        }
        boolean checkNulls = test.root.parent == null &&
                test.root.younger.younger == null && test.root.younger.older == null &&
                test.root.older.younger == null && test.root.older.older == null;
        if (!checkNulls) {
            errorBuilder.append("Null values are not assigned correctly.");
        }
        if (!checkDogs || !checkParents || !checkNulls) {
            System.out.println(errorBuilder.toString());
        } else {
            System.out.println("Passed.");
        }
    }


    private static void testShelter2() {
        Dog R = new Dog("Rex", 8, 100, 5, 50.0);
        Dog S = new Dog("Stella", 5, 50, 2, 250.0);
        Dog L = new Dog("Lessie", 3, 70, 9, 25.0);
        Dog P2 = new Dog("Poldo 2", 7, 60, 1, 35.0);

        DogShelter test = new DogShelter(R);
        DogShelter.DogNode nodeS = test.new DogNode(S);
        test.root.younger = nodeS;
        nodeS.parent = test.root;

        System.out.print("Testing shelter() #2 [1 (left / right) tree rotation]... ");
        test.shelter(L);
        test.shelter(P2);

        StringBuilder errorBuilder = new StringBuilder();
        boolean checkDogs = test.root.d == R && test.root.younger.d == L &&
                test.root.younger.older.d == P2 && test.root.younger.older.younger.d == S;
        if (!checkDogs) {
            errorBuilder.append("Dogs are not assigned correctly. ");
        }
        boolean checkParents = test.root.younger.parent.d == R && test.root.younger.older.parent.d == L &&
                test.root.younger.older.younger.parent.d == P2;
        if (!checkParents) {
            errorBuilder.append("Parent pointers are not assigned correctly. ");
        }
        boolean checkNulls = test.root.parent == null && test.root.older == null &&
                test.root.younger.younger == null &&
                test.root.younger.older.older == null &&
                test.root.younger.older.younger.younger == null && test.root.younger.older.younger.older == null;
        if (!checkNulls) {
            errorBuilder.append("Null values are not assigned correctly.");
        }
        if (!checkDogs || !checkParents || !checkNulls) {
            System.out.println(errorBuilder.toString());
        } else {
            System.out.println("Passed.");
        }
    }


    private static void testShelter3() {
        Dog R = new Dog("Rex", 8, 100, 5, 50.0);
        Dog S = new Dog("Stella", 5, 50, 2, 250.0);
        Dog L = new Dog("Lessie", 3, 70, 9, 25.0);

        DogShelter test = new DogShelter(L);
        DogShelter.DogNode nodeS = test.new DogNode(S);
        test.root.older = nodeS;
        nodeS.parent = test.root;

        System.out.print("Testing shelter() #3 [2 (left) tree rotations]... ");
        test.shelter(R);

        StringBuilder errorBuilder = new StringBuilder();
        boolean checkDogs = test.root.d == R && test.root.younger.d == L && test.root.younger.older.d == S;
        if (!checkDogs) {
            errorBuilder.append("Dogs are not assigned correctly. ");
        }
        boolean checkParents = test.root.younger.parent.d == R && test.root.younger.older.parent.d == L;
        if (!checkParents) {
            errorBuilder.append("Parent pointers are not assigned correctly. ");
        }
        boolean checkNulls = test.root.parent == null && test.root.older == null &&
                test.root.younger.younger == null &&
                test.root.younger.older.younger == null && test.root.younger.older.older == null;
        if (!checkNulls) {
            errorBuilder.append("Null values are not assigned correctly.");
        }
        if (!checkDogs || !checkParents || !checkNulls) {
            System.out.println(errorBuilder.toString());
        } else {
            System.out.println("Passed.");
        }
    }


    private static void testShelter4() {
        Dog R2 = new Dog("Rex 2", 8, 70, 5, 50.0);
        Dog S = new Dog("Stella", 5, 50, 2, 250.0);
        Dog L2 = new Dog("Lessie 2", 3, 80, 9, 25.0);
        Dog P = new Dog("Poldo", 10, 60, 1, 35.0);

        DogShelter test = new DogShelter(L2);
        DogShelter.DogNode nodeP = test.new DogNode(P);
        DogShelter.DogNode nodeS = test.new DogNode(S);
        test.root.older = nodeP;
        nodeP.parent = test.root;
        nodeP.younger = nodeS;
        nodeS.parent = nodeP;

        System.out.print("Testing shelter() #4 [2 (left + right) tree rotations]... ");
        test.shelter(R2);

        StringBuilder errorBuilder = new StringBuilder();
        boolean checkDogs = test.root.d == L2 && test.root.older.d == R2 &&
                test.root.older.younger.d == S && test.root.older.older.d == P;
        if (!checkDogs) {
            errorBuilder.append("Dogs are not assigned correctly. ");
        }
        boolean checkParents = test.root.older.parent.d == L2 &&
                test.root.older.younger.parent.d == R2 && test.root.older.older.parent.d == R2;
        if (!checkParents) {
            errorBuilder.append("Parent pointers are not assigned correctly. ");
        }
        boolean checkNulls = test.root.parent == null && test.root.younger == null &&
                test.root.older.younger.younger == null && test.root.older.younger.older == null &&
                test.root.older.older.younger == null && test.root.older.older.older == null;
        if (!checkNulls) {
            errorBuilder.append("Null values are not assigned correctly.");
        }
        if (!checkDogs || !checkParents || !checkNulls) {
            System.out.println(errorBuilder.toString());
        } else {
            System.out.println("Passed.");
        }
    }


    private static void testAdopt1() {
        Dog R = new Dog( "Rex",8,100,5,50.0 );
        Dog S = new Dog( "Stella",5,50,2,250.0 );
        Dog L = new Dog( "Lessie",3, 70, 9,25.0 );

        DogShelter test = new DogShelter( R );
        DogShelter.DogNode ll = test.new DogNode( L );
        DogShelter.DogNode rr = test.new DogNode( R );
        DogShelter.DogNode ss = test.new DogNode( S );

        test.root.younger = ll;
        test.root.younger.parent = test.root;
        test.root.younger.older = ss;
        test.root.younger.older.parent = ll;

        System.out.print( "Testing adopt() #1... " );
        test.adopt( R );
        System.out.print(test.root.older);

        boolean dogs = test.root.d == L && test.root.older.d == S;
        boolean nulls = test.root.parent == null && test.root.younger == null && test.root.older.younger == null && test.root.older.older == null;
        boolean parents = test.root.older.parent.d == L;

        if( !( dogs && nulls && parents ) )
        {
            if( !dogs ) System.out.println( "Dogs are not assigned correctly" );
            else if( !nulls ) System.out.println( "Null values are not assigned correctly" );
            else if( !parents ) System.out.println( "Parent pointers are not assigned correctly" );
        } else {
            System.out.println("Passed.");
        }
    }
    private static void testAdopt1a() {
        Dog R = new Dog( "Rex",8,100,5,50.0 );
        Dog S = new Dog( "Stella",2,50,2,250.0 );
        Dog L = new Dog( "Lessie",3, 70, 9,25.0 );

        DogShelter test = new DogShelter( R );
        DogShelter.DogNode ll = test.new DogNode( L );
        DogShelter.DogNode rr = test.new DogNode( R );
        DogShelter.DogNode ss = test.new DogNode( S );

        test.root.younger = ll;
        test.root.younger.parent = test.root;
        test.root.younger.younger = ss;
        test.root.younger.younger.parent = ll;

        System.out.print( "Testing adopt() #1a... " );
        test.adopt( L );

        boolean dogs = test.root.d == R && test.root.younger.d == S;
        boolean nulls = test.root.parent == null && test.root.older == null && test.root.younger.younger == null ;
        boolean parents = test.root.younger.parent.d == R;

        if( !( dogs && nulls && parents ) )
        {
            if( !dogs ) System.out.println( "Dogs are not assigned correctly" );
            else if( !nulls ) System.out.println( "Null values are not assigned correctly" );
            else if( !parents ) System.out.println( "Parent pointers are not assigned correctly" );
        } else {
            System.out.println("Passed.");
        }
    }
    private static void testAdopt1b() {
        Dog R = new Dog( "Rex",8,100,5,50.0 );
        Dog S = new Dog( "Stella",2,50,2,250.0 );
        Dog L = new Dog( "Lessie",3, 70, 9,25.0 );

        DogShelter test = new DogShelter( R );
        DogShelter.DogNode ll = test.new DogNode( L );
        DogShelter.DogNode rr = test.new DogNode( R );
        DogShelter.DogNode ss = test.new DogNode( S );

        test.root.younger = ll;
        test.root.younger.parent = test.root;
        test.root.younger.younger = ss;
        test.root.younger.younger.parent = ll;

        System.out.print( "Testing adopt() #1a... " );
        test.adopt( S );

        boolean dogs = test.root.d == R && test.root.younger.d == L;
        boolean nulls = test.root.parent == null && test.root.older == null && test.root.younger.younger == null ;
        boolean parents = test.root.younger.parent.d == R;

        if( !( dogs && nulls && parents ) )
        {
            if( !dogs ) System.out.println( "Dogs are not assigned correctly" );
            else if( !nulls ) System.out.println( "Null values are not assigned correctly" );
            else if( !parents ) System.out.println( "Parent pointers are not assigned correctly" );
        } else {
            System.out.println("Passed.");
        }
    }

    private static void testAdopt2() {
        Dog R = new Dog( "Rex",8,100,5,50.0 );
        Dog S = new Dog( "Stella",5,50,2,250.0 );
        Dog L = new Dog( "Lessie",3, 70, 9,25.0 );
        Dog P = new Dog( "Poldo",10,60,1,35.0 );

        DogShelter test = new DogShelter( R );
        DogShelter.DogNode ll = test.new DogNode( L );
        DogShelter.DogNode rr = test.new DogNode( R );
        DogShelter.DogNode ss = test.new DogNode( S );
        DogShelter.DogNode pp = test.new DogNode( P );

        test.root.younger = ll;
        test.root.younger.parent = test.root;
        test.root.older = pp;
        test.root.older.parent = test.root;
        test.root.younger.older = ss;
        test.root.younger.older.parent = ll;

        System.out.print( "Testing adopt() #2... " );
        test.adopt( R );

        boolean dogs = test.root.d == L && test.root.older.d == P && test.root.older.younger.d == S;
        boolean nulls = test.root.parent == null && test.root.younger == null && test.root.older.older == null && test.root.older.younger.younger == null && test.root.older.younger.older == null;
        boolean parents = test.root.older.parent.d == L && test.root.older.younger.parent.d == P;

        if( !( dogs && nulls && parents ) )
        {
            if( !dogs ) System.out.println( "Dogs are not assigned correctly" );
            else if( !nulls ) System.out.println( "Null values are not assigned correctly" );
            else if( !parents ) System.out.println( "Parent pointers are not assigned correctly" );
        } else {
            System.out.println("Passed.");
        }
    }


    private static void testAdopt3() {
        Dog L = new Dog("Lessie", 3, 70, 9, 25.0);
        Dog R = new Dog("Rex", 8, 100, 5, 50.0);
        Dog S = new Dog("Stella", 5, 50, 2, 250.0);
        Dog P = new Dog("Poldo", 10, 60, 1, 35.0);
        Dog B = new Dog("Bella", 1, 55, 15, 120.0);
        Dog C = new Dog("Cloud", 4, 10, 23, 80.0);
        Dog A = new Dog("Archie", 6, 120, 18, 40.0);
        Dog D = new Dog("Daisy", 7, 15, 12, 35.0);

        DogShelter test = new DogShelter( A );

        DogShelter.DogNode ll = test.new DogNode( L );
        DogShelter.DogNode rr = test.new DogNode( R );
        DogShelter.DogNode ss = test.new DogNode( S );
        DogShelter.DogNode pp = test.new DogNode( P );
        DogShelter.DogNode bb = test.new DogNode( B );
        DogShelter.DogNode cc = test.new DogNode( C );
        DogShelter.DogNode aa = test.new DogNode( A );
        DogShelter.DogNode dd = test.new DogNode( D );

        test.root.younger = ll;
        test.root.younger.parent = test.root;
        test.root.older = rr;
        test.root.older.parent = test.root;

        test.root.younger.younger = bb;
        test.root.younger.younger.parent = ll;
        test.root.younger.older = ss;
        test.root.younger.older.parent = ll;

        test.root.younger.older.younger = cc;
        test.root.younger.older.younger.parent = ss;

        test.root.older.younger = dd;
        test.root.older.younger.parent = rr;
        test.root.older.older = pp;
        test.root.older.older.parent = rr;

        System.out.print( "Testing adopt() #3... " );

        Dog a = test.adopt();

        boolean dogs = test.root.d == R && test.root.younger.d == L && test.root.older.d == P &&
                test.root.younger.younger.d == B && test.root.younger.older.d == S && test.root.younger.older.younger.d == C &&
                test.root.younger.older.older.d == D;

        boolean nulls = test.root.parent == null && test.root.older.younger == null && test.root.older.older == null &&
                test.root.younger.younger.younger == null && test.root.younger.younger.older == null &&
                test.root.younger.older.younger.younger == null && test.root.younger.older.younger.older == null &&
                test.root.younger.older.older.younger == null && test.root.younger.older.older.older == null;

        boolean parents = test.root.younger.parent.d == R &&
                test.root.older.parent.d == R &&
                test.root.younger.younger.parent.d == L &&
                test.root.younger.older.parent.d == L  &&
                test.root.younger.older.younger.parent.d == S &&
                test.root.younger.older.older.parent.d == S;
        boolean ret = a == A;

        if( !( dogs && nulls && parents && ret ) )
        {
            if( !dogs ) System.out.println( "Dogs are not assigned correctly" );
            else if( !nulls ) System.out.println( "Null values are not assigned correctly" );
            else if( !parents ) System.out.println( "Parent pointers are not assigned correctly" );
            else if( !ret ) System.out.println( "The method returned incorrect value" );
        } else {
            System.out.println("Passed.");
        }
    }


    private static void testFindOldest1() {
        Dog R = new Dog( "Rex",8,100,5,50.0 );
        Dog S = new Dog( "Stella",5,150,0,250.0 );
        Dog L = new Dog( "Lessie",3,70,1,25.0 );

        DogShelter test = new DogShelter( S );
        DogShelter.DogNode ll = test.new DogNode( L );
        DogShelter.DogNode rr = test.new DogNode( R );
        DogShelter.DogNode ss = test.new DogNode( S );

        test.root.younger = ll;
        test.root.younger.parent = test.root;
        test.root.older = rr;
        test.root.older.parent = test.root;

        System.out.print( "Testing findOldest() #1... " );

        Dog d = test.findOldest();


        if(!(d.equals(R))) {
            System.out.println( "The dog found was not the oldest." );
        } else {
            System.out.println("Passed.");
        }
    }


    private static void testFindYoungest1() {
        Dog R = new Dog( "Rex",8,100,5,50.0 );
        Dog S = new Dog( "Stella",5,50,0,250.0 );
        Dog L = new Dog( "Lessie",3,70,1,25.0 );

        DogShelter test = new DogShelter( S );
        DogShelter.DogNode ll = test.new DogNode( L );
        DogShelter.DogNode rr = test.new DogNode( R );
        DogShelter.DogNode ss = test.new DogNode( S );

        test.root.younger = ll;
        test.root.younger.parent = test.root;
        test.root.older = rr;
        test.root.older.parent = test.root;

        System.out.print( "Testing findYoungest() #1... " );

        Dog d = test.findYoungest();


        if(!(d.equals(L))) {
            System.out.println( "The dog found was not the youngest." );
        } else {
            System.out.println("Passed.");
        }
    }


    private static void testFindDogToAdopt1() {
        Dog R = new Dog( "Rex",8,100,5,50.0 );
        Dog S = new Dog( "Stella",5,50,0,250.0 );
        Dog L = new Dog( "Lessie",3,70,1,25.0 );

        DogShelter test = new DogShelter(R);
        DogShelter.DogNode ll = test.new DogNode(L);
        DogShelter.DogNode rr = test.new DogNode(R);
        DogShelter.DogNode ss = test.new DogNode(S);

        test.root.younger = ll;
        test.root.younger.parent = test.root;
        test.root.younger.older = ss;
        test.root.younger.older.parent = ll;

        System.out.print( "Testing findDogToAdopt() #1... " );

        Dog d = test.findDogToAdopt(3,7);


        if(!(d.equals(L))) {
            if(!(d.getAge()<=7 && d.getAge()>=3)) {
                System.out.println("The dog found was not the correct age range.");
            }
            System.out.println("The dog found did not have the highest priority within the age range.");
        } else {
            System.out.println("Passed.");
        }
    }


    private static void testBudgetVetExpenses1() {
        Dog R = new Dog("Rex", 8, 100, 5, 50.0);
        Dog S = new Dog("Stella", 5, 50, 2, 250.0);
        Dog L = new Dog("Lessie", 3, 70, 9, 25.0);
        Dog P = new Dog("Poldo", 10, 60, 1, 35.0);
        Dog B = new Dog("Bella", 1, 55, 15, 120.0);
        Dog C = new Dog("Cloud", 4, 10, 23, 80.0);
        Dog A = new Dog("Archie", 6, 120, 18, 40.0);
        Dog D = new Dog("Daisy", 7, 15, 12, 35.0);

        DogShelter test = new DogShelter(A);
        DogShelter.DogNode nodeL = test.new DogNode(L);
        DogShelter.DogNode nodeR = test.new DogNode(R);
        DogShelter.DogNode nodeB = test.new DogNode(B);
        DogShelter.DogNode nodeS = test.new DogNode(S);
        DogShelter.DogNode nodeD = test.new DogNode(D);
        DogShelter.DogNode nodeP = test.new DogNode(P);
        DogShelter.DogNode nodeC = test.new DogNode(C);
        test.root.younger = nodeL;
        nodeL.parent = test.root;
        nodeL.younger = nodeB;
        nodeB.parent = nodeL;
        nodeL.older = nodeS;
        nodeS.parent = nodeL;
        nodeS.younger = nodeC;
        nodeC.parent = nodeS;
        test.root.older = nodeR;
        nodeR.parent = test.root;
        nodeR.younger = nodeD;
        nodeD.parent = nodeR;
        nodeR.older = nodeP;
        nodeP.parent = nodeR;

        System.out.print("Testing budgetVetExpenses() #1... ");
        double result = test.budgetVetExpenses(19);

        double expected = 555.0;
        if (result != expected) {
            System.out.println("Incorrect amount of dollars (expected: " +
                    expected + ", received: " + result + ")");
        } else {
            System.out.println("Passed.");
        }
    }


    private static void testGetVetSchedule1() {
        Dog R = new Dog( "Rex",8,100,5,50.0 );
        Dog S = new Dog( "Stella",5,50,0,250.0 );
        Dog L = new Dog( "Lessie",3,70,1,25.0 );

        DogShelter test = new DogShelter( R );
        DogShelter.DogNode ll = test.new DogNode( L );
        DogShelter.DogNode rr = test.new DogNode( R );
        DogShelter.DogNode ss = test.new DogNode( S );

        test.root.younger = ll;
        test.root.younger.parent = test.root;
        test.root.younger.older = ss;
        test.root.younger.older.parent = ll;

        System.out.print( "Testing getVetSchedule() #1... " );

        ArrayList<ArrayList< Dog >> ald =  test.getVetSchedule();

        boolean arlSizes = ald.size() == 1 && ald.get( 0 ).size() == 3;
        boolean arlDogs = ald.get( 0 ).get( 0 ) == L && ald.get( 0 ).get( 1 ) == S && ald.get( 0 ).get( 2 ) == R;


        if( !( arlSizes && arlDogs ) )
        {
            if( !arlSizes ) System.out.println( "At least one ArrayList has wrong number of elements" );
            else if( !arlDogs ) System.out.println( "Incorrect elements in ArrayList 0" );
        } else {
            System.out.println("Passed.");
        }
    }


    private static void testGetVetSchedule2() {
        Dog L = new Dog("Lessie", 3, 70, 9, 25.0);
        Dog R = new Dog("Rex", 8, 100, 5, 50.0);
        Dog S = new Dog("Stella", 5, 50, 2, 250.0);
        Dog P = new Dog("Poldo", 10, 60, 1, 35.0);
        Dog B = new Dog("Bella", 1, 55, 15, 120.0);
        Dog C = new Dog("Cloud", 4, 10, 23, 80.0);
        Dog A = new Dog("Archie", 6, 120, 18, 40.0);
        Dog D = new Dog("Daisy", 7, 15, 12, 35.0);

        DogShelter test = new DogShelter( A );

        DogShelter.DogNode ll = test.new DogNode( L );
        DogShelter.DogNode rr = test.new DogNode( R );
        DogShelter.DogNode ss = test.new DogNode( S );
        DogShelter.DogNode pp = test.new DogNode( P );
        DogShelter.DogNode bb = test.new DogNode( B );
        DogShelter.DogNode cc = test.new DogNode( C );
        DogShelter.DogNode aa = test.new DogNode( A );
        DogShelter.DogNode dd = test.new DogNode( D );

        test.root.younger = ll;
        test.root.younger.parent = test.root;
        test.root.older = rr;
        test.root.older.parent = test.root;

        test.root.younger.younger = bb;
        test.root.younger.younger.parent = ll;
        test.root.younger.older = ss;
        test.root.younger.older.parent = ll;

        test.root.younger.older.younger = cc;
        test.root.younger.older.younger.parent = ss;

        test.root.older.younger = dd;
        test.root.older.younger.parent = rr;
        test.root.older.older = pp;
        test.root.older.older.parent = rr;

        System.out.print( "Testing getVetSchedule() #2... " );

        ArrayList< ArrayList< Dog > > ald =  test.getVetSchedule();

        boolean arlSizes = ald.size() == 4 && ald.get( 0 ).size() == 3 && ald.get( 1 ).size() == 2 && ald.get( 2 ).size() == 2 && ald.get( 3 ).size() == 1;
        boolean arlDogs1 = ald.get( 0 ).get( 0 ) == S && ald.get( 0 ).get( 1 ) == R && ald.get( 0 ).get( 2 ) == P;
        boolean arlDogs2 = ald.get( 1 ).get( 0 ) == L && ald.get( 1 ).get( 1 ) == D;
        boolean arlDogs3 = ald.get( 2 ).get( 0 ) == B && ald.get( 2 ).get( 1 ) == A;
        boolean arlDogs4 = ald.get( 3 ).get( 0 ) == C;


        if( !( arlSizes && arlDogs1 && arlDogs2 && arlDogs3 && arlDogs4 ) )
        {
            if( !arlSizes ) System.out.println( "At least one ArrayList has wrong number of elements" );
            else if( !arlDogs1 ) System.out.println( "Incorrect elements in ArrayList 0" );
            else if( !arlDogs2 ) System.out.println( "Incorrect elements in ArrayList 1" );
            else if( !arlDogs3 ) System.out.println( "Incorrect elements in ArrayList 2" );
            else if( !arlDogs4 ) System.out.println( "Incorrect elements in ArrayList 3" );
        } else {
            System.out.println("Passed.");
        }
    }


    private static void testDogShelterIterator1() {
        Dog R = new Dog("Rex", 8, 100, 5, 50.0);
        Dog S = new Dog("Stella", 5, 50, 0, 250.0);
        Dog L = new Dog("Lessie", 3, 40, 1, 25.0);

        DogShelter test = new DogShelter(R);
        DogShelter.DogNode ll = test.new DogNode(L);
        DogShelter.DogNode rr = test.new DogNode(R);
        DogShelter.DogNode ss = test.new DogNode(S);

        test.root.younger = ss;
        test.root.younger.parent = test.root;
        test.root.younger.younger = ll;
        test.root.younger.younger.parent = ss;

        ArrayList<Dog> expected = new ArrayList<>();
        expected.add(L);
        expected.add(S);
        expected.add(R);


        ArrayList<Dog> actual = new ArrayList<>();

        System.out.print("Testing DogShelterIterator #1... ");

        boolean error = false;
        for (Dog d: test) {
            actual.add(d);
        }
        if (actual.isEmpty() || actual.size() < expected.size()) {
            error = true;
            System.out.println("Did not visit all of the dogs in the shelter.");
        }
        if (actual.size() > expected.size()) {
            error = true;
            System.out.println("Some dogs were visited multiple times.");
        }
        for (int i=0; i<3; i++) {
            if(!(actual.get(i).equals(expected.get(i)))) {
                error = true;
                System.out.println("Dogs were visited in the wrong order");
            }
        }

        if (!error) {
            System.out.println("Passed.");
        }
    }


    private static void testDogShelterIterator2() {
        Dog R = new Dog("Rex", 8, 100, 5, 50.0);
        Dog S = new Dog("Stella", 5, 50, 0, 250.0);
        Dog L = new Dog("Lessie", 3, 70, 1, 25.0);
        Dog F = new Dog("Fred", 6, 120, 10, 100.0);

        DogShelter test = new DogShelter(F);
        DogShelter.DogNode ll = test.new DogNode(L);
        DogShelter.DogNode rr = test.new DogNode(R);
        DogShelter.DogNode ss = test.new DogNode(S);
        DogShelter.DogNode ff = test.new DogNode(F);

        test.root.younger = ll;
        test.root.younger.parent = test.root;
        test.root.younger.older = ss;
        test.root.younger.older.parent = ll;
        test.root.older = rr;
        test.root.older.parent = ff;

        ArrayList<Dog> expected = new ArrayList<>();
        expected.add(L);
        expected.add(S);
        expected.add(F);
        expected.add(R);


        ArrayList<Dog> actual = new ArrayList<>();

        System.out.print("Testing DogShelterIterator #2... ");

        boolean error = false;
        for (Dog d: test) {
            actual.add(d);
        }
        if (actual.isEmpty() || actual.size() < expected.size()) {
            error = true;
            System.out.println("Did not visit all of the dogs in the shelter.");
        }
        if (actual.size() > expected.size()) {
            error = true;
            System.out.println("Some dogs were visited multiple times.");
        }
        for (int i=0; i<4; i++) {
            if(!(actual.get(i).equals(expected.get(i)))) {
                error = true;
                System.out.println("Dogs were visited in the wrong order");
            }
        }

        if (!error) {
            System.out.println("Passed.");
        }
    }


    private static void testDogShelterIterator3() {
        Dog R = new Dog("Rex", 8, 100, 5, 50.0);

        DogShelter test = new DogShelter(R);

        ArrayList<Dog> actual = new ArrayList<>();

        System.out.print("Testing DogShelterIterator #3, contains only a root node... ");

        boolean error = false;
        for (Dog d: test) {
            actual.add(d);
        }
        if (actual.isEmpty()) {
            error = true;
            System.out.println("Did not visit any dog in the shelter.");
        }
        if (actual.size() > 1) {
            error = true;
            System.out.println("Some dogs were visited multiple times.");
        }
        if (!(actual.get(0).equals(R))){
            error = true;
            System.out.println("Incorrect dog was visited.");
        }

        if (!error) {
            System.out.println("Passed.");
        }
    }
}
