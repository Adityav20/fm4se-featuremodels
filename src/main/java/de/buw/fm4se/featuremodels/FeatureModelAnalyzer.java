package de.buw.fm4se.featuremodels;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import de.buw.fm4se.featuremodels.exec.LimbooleExecutor;
import de.buw.fm4se.featuremodels.fm.CrossTreeConstraint;
import de.buw.fm4se.featuremodels.fm.Feature;
import de.buw.fm4se.featuremodels.fm.FeatureModel;

/**
 * This code needs to be implemented by translating FMs to input for Limboole
 * and interpreting the output
 *
 */
public class FeatureModelAnalyzer {

  public static boolean checkConsistent(FeatureModel fm) {
    String formula = FeatureModelTranslator.translateToFormula(fm);
    
    String result;
    try {
      result = LimbooleExecutor.runLimboole(formula, true);
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }
    if (result.contains("UNSATISFIABLE")) {
      return false;
    }
    return true;
  }

  public static List<String> deadFeatureNames(FeatureModel fm) throws Exception {
    List<String> deadFeatures = new ArrayList<>();

    // TODO check for dead features
    checkDeadFeature(fm.getRoot(), deadFeatures, fm.getConstraints());
    
    // If the Dead List contain the root -> add all of the children
    if(deadFeatures.contains(fm.getRoot().getName())){
      checkListDead(fm.getRoot(), deadFeatures);
    }
    
    System.out.println(Arrays.toString(deadFeatures.toArray()));
    return deadFeatures;
  }

  public static void checkListDead(Feature feature, List<String> deadFeatures){
    if(deadFeatures.contains(feature.getName())){
      for(Feature f : feature.getChildren()){
        System.out.println("this is " + f.getName());
        deadFeatures.add(f.getName());

        checkListDead(f,deadFeatures);
      }
    }
  }

  private static void checkDeadFeature(Feature feature, List<String> deadFeatures, List<CrossTreeConstraint> ltCons){
    FeatureModel temp = new FeatureModel();
    temp.setRoot(feature);
    
    for(CrossTreeConstraint c : ltCons) {
      temp.addConstraint(c);
      }

    if(!checkConsistent(temp)){
      deadFeatures.add(feature.getName());
    }
    
    for (Feature child : feature.getChildren()) {
      checkDeadFeature(child, deadFeatures, ltCons);
    }
  }

  public static List<String> mandatoryFeatureNames(FeatureModel fm) {
    List<String> mandatoryFeatures = new ArrayList<>();

    mandatoryFeatures.add(fm.getRoot().getName());
    checkMandatoryFM(fm.getRoot(), fm.getRoot().getChildren(), mandatoryFeatures, fm.getRoot().getName());

    return mandatoryFeatures;
  }

  public static void checkMandatoryFM(Feature feature, List<Feature> listChild, List<String> mandatoryFeatures, String rootName){
    for (Feature child : feature.getChildren()){
      if(feature.getName() == rootName){
        if(child.isMandatory()){
          mandatoryFeatures.add(child.getName());
        }
      }else{
        if(child.isMandatory() && feature.isMandatory()){
          mandatoryFeatures.add(child.getName());
        }
      }
    }

    for (Feature f : feature.getChildren()){
      checkMandatoryFM(f, f.getChildren(), mandatoryFeatures, rootName);
    }
  }

}
