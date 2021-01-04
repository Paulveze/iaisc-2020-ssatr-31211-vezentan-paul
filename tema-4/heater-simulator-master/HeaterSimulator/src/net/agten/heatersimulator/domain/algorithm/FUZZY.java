package net.agten.heatersimulator.domain.algorithm;

import net.agten.heatersimulator.controller.dto.FuzzyParameters;
import net.sourceforge.jFuzzyLogic.FIS;
import net.sourceforge.jFuzzyLogic.FunctionBlock;

public class FUZZY implements ControllerAlgorithm {
    private int targetAdc;
    private FunctionBlock functionBlock;
    FIS fuzzyInferenceSystem;

    public FUZZY(FuzzyParameters parameters) {
        this.fuzzyInferenceSystem = FIS.load("./FISHeater.fcl", true);
        if (fuzzyInferenceSystem == null) {
            System.err.println("Error loading the file.");
            System.exit(1);
        }

        functionBlock = fuzzyInferenceSystem.getFunctionBlock(null);
        functionBlock.setVariable("power", parameters.getPower());
    }

    @Override
    public short nextValue(short curAdc) {
        int error = this.targetAdc - curAdc;

        functionBlock.setVariable("error", -error/3.3333333);

        functionBlock.evaluate();

        functionBlock.getVariable("command").defuzzify();


        double result = functionBlock.getVariable("command").getValue();

        System.out.println("Error: "+ -error/3.3333333 + " Command: "+ functionBlock.getVariable("command").getValue());

        return (short)Math.max(result, 0);
    }

    @Override
    public void setTargetAdc(short targetAdc) {
        this.targetAdc = targetAdc;
    }
}
