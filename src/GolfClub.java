import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Scanner;

public class GolfClub{
    private static HashMap<String,Integer> undo=new HashMap<>();
    private static HashMap<String,Integer> undo2=new HashMap<>();
    private static Scanner scn= new Scanner(System.in); //create new scanner
    private static Scanner namescn= new Scanner(System.in);
    private static ArrayList<String> golfer = new ArrayList<>(); //create arrayList for storing golfer names
    private static ArrayList<String> delGolfer = new ArrayList<>(); //create arrayList for storing golfer names
    private static ArrayList<Integer> score = new ArrayList<>();  //create arrayList for adding golfer scores
    private static ArrayList<Integer> delScore = new ArrayList<>();  //create arrayList for adding golfer scores
    private static ArrayList<Integer> nameLength= new ArrayList<>(); //arrayList for golfer name lengths
    private static ArrayList<Integer> tempArray = new ArrayList<>(); //create a temporary arrayList
    private static int subSelect;
    private static int recent=0;
    private static int counter=0;
    public static void main(String[] args) {
        System.out.println();
        System.out.println("Welcome to Springfield Golf Club.");
        int selection;
        do {
            //print menu
            System.out.println("Select Option:");
            System.out.println("1.Enter Scores");
            System.out.println("2.Find Golfer");
            System.out.println("3.Display Scoreboard");
            System.out.println("4.Exit Program");
            System.out.println(">");
            System.out.print("Enter your option: ");
            inputValidation();
            selection = scn.nextInt();
            System.out.println("");

            switch (selection) {
                case 1:
                    do{
                        subMenu();
                        System.out.println();
                        System.out.println();
                    }while(subSelect!=7);
                    break;
                case 2:
                    findGolfer();
                    break;
                case 3:
                    scoreBoard();
                    break;
                case 4:
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid Option..Please Re-enter");
            }
            System.out.println();
            System.out.println();
        } while((selection < 1 || selection > 4) || selection!=4) ;
    }


    private static void enterScores() {
        int golferNum;
        int count=0;
        int golferScore;
        undo.clear();  //remove all the elements in the undo HashMap
        System.out.print("Enter number of golfers: ");
        inputValidation(); //validate for integer input
        golferNum=scn.nextInt();
        while(golferNum<0) {//check if entered integer is negative
            System.out.print("Please enter a positive value: ");
            while(!scn.hasNextInt()){  //check for integer inputs (scanner has an integer in it)
                System.out.print("Please enter an integer value: ");
                scn.next();
            }
            golferNum=scn.nextInt();
        }
        recent=1;
        while(count<golferNum){
            System.out.print("Enter golfer's name: ");
            String golferName=namescn.nextLine();
            //modify or keep already existing records
            if (golfer.contains(golferName)) {
                System.out.println("Golfer already found in the program");
                System.out.print("Enter Y to modify or N to keep existing data: ");
                String answer=scn.next().toLowerCase();
                char result=answer.charAt(0); //read character input for switch case
                switch(result){
                    case 'y':
                        int golferIndex=golfer.indexOf(golferName);
                        //get current index of already existing golfer in golfer arrayList
                        System.out.print("Enter score: ");
                        inputValidation(); //check for integer input
                        golferScore=scn.nextInt();
                        //check for range of input
                        golferScore= rangeCheck(golferScore);
                        //replace already existing element with new element in score arrayList
                        score.set(golferIndex,golferScore);
                        undo.put(golferName,golferScore);  // add golfer names and scores to the undo HashMap
                        count++;
                        break;
                    case 'n':
                        System.out.println("You can continue with next golfer");
                        break;
                    default:
                        System.out.println("Invalid answer.Please reenter golfer's name");
                        break;
                }
            }
            else{
                golfer.add(golferName); //add new golfer to golfer arrayList
                System.out.print("Enter score: ");
                inputValidation(); //check for integer inputs
                golferScore=scn.nextInt();
                //check range of integer input
                golferScore=rangeCheck(golferScore);
                score.add(golferScore);  //add new golfer score to score arrayList
                undo.put(golferName,golferScore); //add golfer names and scores to the undo HashMap
                count++;
            }
            counter=0;
        }
    }

    private static void findGolfer(){
        if(golfer.size()<=0){
            System.out.print("Sorry, No records found.");
        }
        else{
            System.out.print("Enter name of the golfer: ");
            String findName=namescn.nextLine();
            if(!golfer.contains(findName)){ //check if golfer is already found in arrayList(golfer)
                System.out.println("Golfer not found in the program.");
                System.out.println();
            }
            else{
                int findIndex=golfer.indexOf(findName); //get index of golfer in arrayList golfer
                System.out.println();
                System.out.println(golfer.get(findIndex)+"  -  "+score.get(findIndex));
            }
        }

    }

    private static void scoreBoard(){
        if(score.size()==0){  //display error message if no golfer is found (score arrayList is empty)
            System.out.println("Sorry, No records found.");
        }
        else{
            ArrayList<Integer> scoreSort= new ArrayList<>(score.size()); //create new arrayList to sort scores
            for(int val : score){ //add all elements of score to scoreSort and tempArray
                scoreSort.add(val);
                tempArray.add(val);
            }
            Collections.sort(scoreSort); //sort arrayList scoreSort in ascending order
            for (String name : golfer) {
                nameLength.add(name.length()); //add golfer name lengths to nameLength arrayList
            }
            Collections.sort(nameLength); //sort arrayList scoreSort in ascending order
            //get maximum length of golfer names - get the last element of sorted nameLength arrayList
            int maxLength=nameLength.get((nameLength.size()-1));
            int spaceNum=(maxLength-2)/2; //no. of spaces calculation (output formatting)
            System.out.println();
            for(int j=0;j<=spaceNum;j++){
                System.out.print(" ");
            }
            System.out.print("Golfer");
            for(int j=0;j<=spaceNum+2;j++){ //print spaces
                System.out.print(" ");
            }
            System.out.println("Score");
            for(int i=0;i<score.size();i++){
                //get index of arrayList tempArray for each element of sorted arrayList scoreSort
                int indexVal=tempArray.indexOf(scoreSort.get(i));
                int spaces=((maxLength-(golfer.get(indexVal)).length())+3); //calculate no. of spaces to print
                //print golfer name corresponding to index of arrayList tempArray
                //index of arrayList tempArray = index of element corresponding to element in arrayList sortScore
                System.out.print("|   "+golfer.get(indexVal)+"  ");
                for(int k=0;k<=spaces;k++){
                    System.out.print(" ");
                }
                if((Integer.toString(tempArray.get(indexVal))).length()<3){
                    //print element of tempArray corresponding to sortScore
                    System.out.println(" "+tempArray.get(indexVal)+"  |");
                }
                else{
                    System.out.println(tempArray.get(indexVal)+"  |");
                }
                //replace printed elements with 111 - a value outside the range
                tempArray.set(indexVal,111);
            }
            scoreSort.clear();
        }
        tempArray.clear(); //remove all elements in tempArray

    }

    private static void inputValidation(){
        while(!scn.hasNextInt()){ //check if the scanner has an integer
            System.out.print("Please enter an integer value: ");
            scn.next(); //clear the scanner
        }
    }

    private static int rangeCheck(int value){
        while(value<18 || value>108){
            System.out.print("Please enter a value between 18 and 108: ");
            while(!scn.hasNextInt()){
                System.out.print("Please enter an integer value: ");
                scn.next();
            }
            value=scn.nextInt();
        }
        return value;
    }


    private static void subMenu(){

        do{
            //Display submenu
            System.out.println("Select Option:");
            System.out.println("1.Enter Player Details");
            System.out.println("2.Edit Data");
            System.out.println("3.Delete Data");
            System.out.println("4.Restore Golfer");
            System.out.println("5.Undo Last Operation");
            System.out.println("6.Redo Operation");
            System.out.println("7.Return to main menu");
            System.out.println("");
            System.out.print("Enter your selection: ");
            inputValidation();
            subSelect = scn.nextInt();
            switch(subSelect){
                case 1:
                    enterScores();
                    break;
                case 2:
                    editData();
                    break;
                case 3:
                    deleteData();
                    break;
                case 4:
                    restoreGolfer();
                    break;
                case 5:
                    undo();
                    break;
                case 6:
                    redo();
                    break;
                case 7:
                    break;
                default:
                    System.out.println("Invalid Option.Please Reenter");
            }
        } while(subSelect < 1 || subSelect >7);
    }

    private static void editData(){
        if(golfer.size()==0){
            System.out.println("No golfers found. Please re-try after entering scores...");
        }
        else{
            recent=2;
            undo.clear(); //remove all the elements in the undo HashMap
            ArrayList<String> edited = new ArrayList<>();
            scoreBoard();
            int editCount=0;
            System.out.print("Enter number of golfers to edit: ");
            inputValidation(); //check for integer inputs
            int editNum=scn.nextInt();
            while(editNum<0 || editNum>golfer.size()){ //check if entered integer is negative
                System.out.print("Please enter a value between 1 and "+golfer.size()+" : ");
                while(!scn.hasNextInt()){  //check for integer inputs (scanner has an integer in it)
                    System.out.print("Please enter an integer value: ");
                    scn.next();
                }
                editNum=scn.nextInt();
            } //check if entered values are negative values
            while(editCount<editNum){
                System.out.print("Enter golfer name to edit: ");
                String editName=namescn.nextLine();
                while(edited.contains(editName)){
                    System.out.print("You already edited the golfer. Please continue with next golfer: ");
                    editName=namescn.nextLine();
                }
                while(!(golfer.contains(editName))){  //check if golfer arrayList contains golfer to edit
                    System.out.print("Please enter a name displayed before: ");
                    editName=namescn.nextLine();   //get new name if gofer name is not found in golfer arrayList
                }
                int editIndex=golfer.indexOf(editName); //find the index value of entered name in golfer arrayList
                System.out.print("Enter new name: ");
                String newName=namescn.nextLine();
                while(golfer.contains(newName)){
                    System.out.print("Golfer found in the program.Please enter a different name: ");
                    newName=namescn.nextLine(); //get new name again if new name is already found in golfers
                }
                System.out.print("Enter new score: ");
                int newScore=scn.nextInt();
                newScore=rangeCheck(newScore);
                golfer.set(editIndex,newName);  //replace golfer name with new name
                undo.put(editName,score.get(editIndex)); //add old golfer names and scores to the undo HashMap
                score.set(editIndex,newScore);  //replace golfer score with new score
                undo2.put(newName,newScore); //add new golfer names and scores to the undo2 HashMap
                edited.add(editName);
                editCount++;

            }
            edited.clear();
            counter=0;
        }
    }

    private static void deleteData(){
        if(golfer.size()==0){
            System.out.println("No golfers found. Please re-try after entering scores...");
        }
        else{
            recent=3;
            scoreBoard(); //displaly scoreboard
            System.out.print("Enter number of golfers to delete: ");
            int deleteNum=scn.nextInt();
            while(deleteNum<0 || deleteNum>golfer.size()){ //check if entered integer is negative
                System.out.print("Please enter a value between 1 and "+golfer.size()+" : ");
                while(!scn.hasNextInt()){  //check for integer inputs (scanner has an integer in it)
                    System.out.print("Please enter an integer value: ");
                    scn.next();
                }
                deleteNum=scn.nextInt();
            }
            undo.clear();  //remove all the elements in the undo HashMap
            for(int d=0; d<deleteNum; d++){
                System.out.print("Enter golfer to delete: ");
                String delName=namescn.nextLine();
                while(delGolfer.contains(delName)){
                    System.out.print("You already deleted the golfer. Please enter a different golfer: ");
                    delName=namescn.nextLine();
                }
                while(!(golfer.contains(delName))){  //check if golfer contains the name to delete
                    System.out.print("Please enter a golfer name found in the program: ");
                    delName=namescn.nextLine();
                }
                int delIndex=golfer.indexOf(delName); //find golfer name to delete in golfer arrayList
                golfer.remove(delName); //remove golfer from golfer arrayList
                delGolfer.add(delName);  //add golfer to deleted golfer arrayList
                int deleteScore=score.get(delIndex);  //get score to be deleted
                score.remove(delIndex);  //remove corresponding score from score arrayList
                delScore.add(deleteScore); //add score to deleted score arrayList
                undo.put(delName,deleteScore);  //add deleted golfer names and scores to undo HashMap
                counter=0;
            }
            delGolfer.clear();
        }
    }

    private static void restoreGolfer(){
        if(delGolfer.size()<=0){
            System.out.println("No golfer deleted to restore"); //display error message if no golfer was deleted before
        }
        else{
            recent=4;
            undo.clear(); //remove all the elements in the undo HashMap
            System.out.print("Eneter golfer name to restore: ");
            String restoreGolfer=namescn.nextLine();
            while(!(delGolfer.contains(restoreGolfer))){//check if golfer is found in deleted golfer
                System.out.print("Please enter a golfer name deleted before: ");
                restoreGolfer=namescn.nextLine();
            }
            //get the index of golfer name to be restored in deleted golfers
            int restoreIndex=delGolfer.indexOf(restoreGolfer);
            golfer.add(delGolfer.get(restoreIndex));  //add deleted golfer to golfer arrayList
            score.add(delScore.get(restoreIndex));   //add corresponding score to score arrayList
            //add restored golfer names and scores to the undo HashMap
            undo.put(delGolfer.get(restoreIndex),delScore.get(restoreIndex));
            counter=0;
        }
    }

    private static void undo(){
        if (recent==0){
            System.out.println("Sorry. Cannot perform undo.");
        }
        else{
            switch (recent){
                case 1:
                    //undo operation for option 1
                    for (String name : undo.keySet()){
                        golfer.remove(name);

                    }
                    for (Integer value : undo.values()){
                        score.remove(value);
                        delScore.add(value);
                    }
                    counter++; //increment counter each time an undo operation is performed
                    break;
                case 2:
                    //undo operation for option 2
                    for (String name : undo2.keySet()){
                        for (String old : undo.keySet()){
                            int nIndex= golfer.indexOf(name);
                            golfer.set(nIndex,old);
                        }
                    }
                    for (Integer newVal : undo2.values()){
                        for (Integer oldVal : undo.values()){
                            int sIndex= score.indexOf(newVal);
                            score.set(sIndex,oldVal);
                        }
                    }
                    counter++;
                    break;
                case 3:
                    undoOp3();//undo operation for option 3
                    counter++;
                    break;
                case 4:
                    //undo operation for option 4
                    redoOp3();
                    counter++;
                    break;
            }
        }
    }

    private static void redo(){
        if (counter==0){
            System.out.println("Sorry. Cannot perform redo. No undo operation performed");
        }
        else{
            switch (recent){
                case 1:
                    //redo operation for option 1
                    golfer.addAll(undo.keySet());
                    score.addAll(undo.values());
                    break;
                case 2:
                    //redo operation for option 2
                    for (String old : undo.keySet()){
                        for (String name : undo2.keySet()){
                            int nIndex= golfer.indexOf(old);
                            golfer.set(nIndex,name);
                        }
                    }
                    for (Integer oldVal : undo.values()){
                        for (Integer newVal : undo2.values()){
                            int sIndex= score.indexOf(oldVal);
                            score.set(sIndex,newVal);
                        }
                    }
                    break;
                case 3:
                    redoOp3();//redo operation for option 3
                    break;
                case 4:
                    undoOp3();//redo operation for option
                    break;
            }

        }
    }


    private static void undoOp3(){
        golfer.addAll(undo.keySet());
        score.addAll(undo.values());
    }

    private static void redoOp3(){
        golfer.removeAll(undo.keySet());
        score.removeAll(undo.values());
    }


}




