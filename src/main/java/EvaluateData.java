import java.util.*;


public class EvaluateData {

    public static Integer getAverages(HashMap<Integer, Integer> originalScores, HashMap<Integer, Integer> retakeScores){
        // pulls highest values between the two exams for each student
        Iterator hmIterator = originalScores.entrySet().iterator();
        ArrayList<Integer> highScores = new ArrayList<Integer>();
        while (hmIterator.hasNext()) {
            Map.Entry mapElement = (Map.Entry) hmIterator.next();
            // checks to see if the student retook the exam
            if(retakeScores.containsKey(mapElement.getKey()) == true){
                int firstScore = (Integer)mapElement.getValue();
                int secondScore = retakeScores.get(mapElement.getKey());
                // checks to see which of the two scores was higher
                // Students second score was higher or equal to
                if(firstScore <= secondScore){
                    highScores.add(secondScore);
                }
                // Students first score was higher
                else if (firstScore > secondScore){
                    highScores.add(firstScore);
                }
            }
            // The student only took the exam once
            else {
                highScores.add((Integer)mapElement.getValue());
            }
        }

        // get the class average
        int sum = 0;
        for(int i=0; i < highScores.size(); i++){
            sum += highScores.get(i);
        }
        int average = sum/highScores.size();
        System.out.println("Class average: "+average+"%");
        return(average);
        }

        public static ArrayList<String> getFemaleCsMajors(ArrayList<ArrayList<String>> studentInfo){
            // pulls ID's of students who are female CS Majors
            ArrayList<String> csFemaleStudents = new ArrayList<String>();
            for(int i=0; i < studentInfo.size();i++){
                String studentID = studentInfo.get(i).get(0);
                String major = studentInfo.get(i).get(1);
                String gender = studentInfo.get(i).get(2);
                if(major.compareTo("computer science")==0 && gender.compareTo("F") == 0){
                    csFemaleStudents.add(studentID);
                }
            }
            // put ID's in ascending order
            Collections.sort(csFemaleStudents);
            System.out.println("Female Computer Science Majors: "+csFemaleStudents);
            return(csFemaleStudents);
        }

}
