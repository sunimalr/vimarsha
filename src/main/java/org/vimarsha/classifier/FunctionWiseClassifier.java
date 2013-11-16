package org.vimarsha.classifier;

import weka.classifiers.Evaluation;
import weka.classifiers.trees.J48;
import weka.core.FastVector;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created with IntelliJ IDEA.
 * User: sunimal
 */
public class FunctionWiseClassifier extends AbstractClassifier {

    private ArrayList<String> funcList=new ArrayList<String>();

    public FunctionWiseClassifier() {
        super();
    }

    @Override
    public HashMap<String,String> classify(ArrayList<String> list) {
        String[] options = new String[4];
        options[0] = "-C";
        options[1] = "0.25";
        options[2] = "-M";
        options[3] = "2";
        J48 tree = new J48();
        FastVector<String> predictions = null;

        try {
            tree.setOptions(options);
            tree.buildClassifier(trainSet);
            Evaluation eval = new Evaluation(trainSet);
            eval.evaluateModel(tree, testSet);
            predictions = eval.predictions();
        } catch (Exception ex) {
            System.out.println(ex.toString());
        }

        HashMap<String,String> functionWiseResult=new HashMap<String, String>();

        if(predictions!=null){
            for (String prediction : predictions) {
                functionWiseResult.put(list.remove(0),prediction);
            }
            return functionWiseResult;
        }else{
            return null;
        }
    }
}
