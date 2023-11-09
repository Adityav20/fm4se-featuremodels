package de.buw.fm4se.featuremodels;

import java.util.ArrayList;
import java.util.List;


import de.buw.fm4se.featuremodels.fm.Feature;
import de.buw.fm4se.featuremodels.fm.FeatureModel;
import de.buw.fm4se.featuremodels.fm.GroupKind;

public class FeatureModelTranslator {
  public static String translateToFormula(FeatureModel fm) {
    
    // TODO implement a real translation
    List<String> ltState = new ArrayList<>();
    List<String> ltConsState = new ArrayList<>();

    String total = "";
    String cons = "";
    String stringTemp = "";
    String numBracket ="";
    

    if (fm.getRoot().getChildren().size() != 0){
      ltState = checkChild(fm.getRoot());
      ltConsState = checkConstraint(fm);

      for(int i = 0; i<ltConsState.size(); i++){
        cons = "( " + ltConsState.get(i) + " )"; 

        for(int j = 0; j<ltState.size(); j++){
          numBracket+= ")";
        }
  
        for(int k = 0; k<ltState.size(); k++){
          String temp = "("+ltState.get(k)+")";
          stringTemp += "( " + temp + " & " ;
        }
        total = stringTemp + cons + numBracket;
      }
      System.out.println("Total Statement : ");
      System.out.println(total);
      
    }else{
      System.out.println("No Child");
      total = fm.getRoot().getName();
    }
    return total;
  }

  public static List<String> checkChild(Feature feature){
    List<String> ltState = new ArrayList<>();
    
    ltState.add(feature.getName());
    checkGroupKind(feature, ltState);
    
    return ltState;
  }

  public static void checkGroupKind(Feature feature, List<String> ltState){
    String head = feature.getName();

    if (feature.getChildGroupKind().equals(GroupKind.XOR)){
      String body = "";
      List<String> lt2Child = new ArrayList<>();
      List<String> listTemp = new ArrayList<>();
      if(feature.getChildren().size() == 2){
        for(int j =0; j<feature.getChildren().size();j++){
          lt2Child.add(feature.getChildren().get(j).getName());
        }
  
        body =  lt2Child.get(0) + " & !" + lt2Child.get(1) + " | !" + lt2Child.get(0) + " & " + lt2Child.get(1) ;
        
        String stateXOR = head + " -> " + body;
        ltState.add(stateXOR);
      }else if(feature.getChildren().size() > 2){
    	  
        checkXOR(feature, listTemp);

        for (String s : listTemp){
          if(s.equals(listTemp.get(listTemp.size()-1))){
            body += s + " ";
          }else{
            body += s + " | " ;
          }
        }
        String stateXOR = head + " -> " + body;
        ltState.add(stateXOR);
      }
      

    } else if (feature.getChildGroupKind().equals(GroupKind.OR)){
      String body = "";
      for (Feature f : feature.getChildren()){
        if(f.equals(feature.getChildren().get(feature.getChildren().size()-1))){
          body += f.getName() + " ";
        }else{
          body += f.getName() + " | " ;
        }
      }
      String stateOR = head + " -> " + body;
      ltState.add(stateOR);
      
    } else{
      for (Feature f : feature.getChildren()) {
        checkIsMandatory(feature, f, ltState);
      }
    }

    for (Feature f : feature.getChildren()){
      checkGroupKind(f,ltState);
    }
    
  }

  public static void checkXOR(Feature feature, List<String> listTemp){
    String temp ="";

    for(int i=0; i <feature.getChildren().size(); i++){
      for(int j=0; j <feature.getChildren().size(); j++){

        if(j != feature.getChildren().size()-1 && feature.getChildren().get(j).getName().equals(feature.getChildren().get(i).getName())){
          temp +=  feature.getChildren().get(j).getName() + " & ";
        }else if(j != feature.getChildren().size()-1 && !feature.getChildren().get(j).getName().equals(feature.getChildren().get(i).getName())){
          temp += "!"+ feature.getChildren().get(j).getName() + " & ";
        }else if(j == feature.getChildren().size()-1 && !feature.getChildren().get(j).getName().equals(feature.getChildren().get(i).getName())){
          temp += "!" + feature.getChildren().get(j).getName();
        }else {
          temp += feature.getChildren().get(j).getName();
        }
      }
      listTemp.add(temp);
      temp="";
    }
  }

  public static void checkIsMandatory(Feature feature, Feature child, List<String> listStatement){
    // Check if it mandatory or not mandatory
    String head = feature.getName();
    if(child.isMandatory() == true){
      String stateM ="("+ child.getName() + " -> " + head + ") & (" + head+ " -> " + child.getName() +")";
      listStatement.add(stateM);
    } else{
      String stateNM = child.getName() + " -> " + head;
      listStatement.add(stateNM);
    }
  }

  public static List<String> checkConstraint(FeatureModel featureModel){
    List<String> ltConsState = new ArrayList<>();
    String consState = "";
    for(int i=0; i<featureModel.getConstraints().size(); i++){
     if(featureModel.getConstraints().get(i).getKind().name() == "REQUIRES"){
        consState = featureModel.getConstraints().get(i).getLeft().getName() + " -> " + featureModel.getConstraints().get(i).getRight().getName();
      } else{
        consState = "!"+featureModel.getConstraints().get(i).getLeft().getName() + " | !" + featureModel.getConstraints().get(i).getRight().getName();
      }
      ltConsState.add(consState);
    }

    return ltConsState;
  }
}
