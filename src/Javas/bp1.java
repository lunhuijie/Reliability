package Javas;

import java.io.File;

import org.joone.engine.FullSynapse;
import org.joone.engine.Monitor;
import org.joone.engine.NeuralNetEvent;
import org.joone.engine.NeuralNetListener;
import org.joone.engine.SigmoidLayer;
import org.joone.engine.learning.TeachingSynapse;
import org.joone.io.FileInputSynapse;
import org.joone.io.FileOutputSynapse;



public class bp1 implements NeuralNetListener{


	public static void Test() {
		// TODO Auto-generated method stub
		bp1 bp = new bp1();
		bp.Go("F:\\SYS.txt","F:\\error.txt");
	}
	public  void Go(String inputFile, String outputFile) {
        /*
         * Firts, creates the three Layers
         */
        SigmoidLayer input = new SigmoidLayer();
        SigmoidLayer middle = new SigmoidLayer();
        SigmoidLayer output = new SigmoidLayer();
        input.setLayerName("input");
        middle.setLayerName("middle");
        output.setLayerName("output");
        /* sets their dimensions */
        input.setRows(1);
        middle.setRows(3);
        output.setRows(1);
        
        /*
         * Now create the two Synapses
         */
        FullSynapse synapse_IH = new FullSynapse(); /* input -> hidden conn. */
        FullSynapse synapse_HO = new FullSynapse();/* hidden -> output conn. */
        
        synapse_IH.setName("IH");
        synapse_HO.setName("HO");
        /*
         * Connect the input layer whit the hidden layer
         */
        input.addOutputSynapse(synapse_IH);
        middle.addInputSynapse(synapse_IH);
        /*
         * Connect the hidden layer whit the output layer
         */
        middle.addOutputSynapse(synapse_HO);
        output.addInputSynapse(synapse_HO);

        /*
         * Create the Monitor object and set the learning parameters
         */
        Monitor monitor = new Monitor();
        monitor.setLearningRate(0.8);
        monitor.setMomentum(0.3);
        /*
         * Passe the Monitor to all components
         */
        input.setMonitor(monitor);
        middle.setMonitor(monitor);
        output.setMonitor(monitor);
        
        /* The application registers itself as monitor's listener
         * so it can receive the notifications of termination from
         * the net.
         */
        monitor.addNeuralNetListener(this);
        
        FileInputSynapse inputStream = new FileInputSynapse();
 
        /* The first two columns contain the input values */
        inputStream.setAdvancedColumnSelector("1");
        
        /* This is the file that contains the input data */
        inputStream.setFileName(inputFile);
        input.addInputSynapse(inputStream);
        
        
        TeachingSynapse trainer = new TeachingSynapse();
        trainer.setMonitor(monitor);
        
        /* Setting of the file containing the desired responses,
         provided by a FileInputSynapse */
        FileInputSynapse samples = new FileInputSynapse();
        samples.setFileName(inputFile);
        /* The output values are on the third column of the file */
        samples.setAdvancedColumnSelector("2");
        // We normalize the input data in the range 0 - 1
        //samples.setPlugIn(new NormalizerPlugIn());

        trainer.setDesired(samples);
        
        /* Creates the error output file */
        FileOutputSynapse error = new FileOutputSynapse();
        error.setFileName(outputFile);
        error.setBuffered(false);
        trainer.addResultSynapse(error);
        
        /* Connects the Teacher to the last layer of the net */
        output.addOutputSynapse(trainer);
        
        /* Creates the results output file */ 
        FileOutputSynapse results = new FileOutputSynapse();
        results.setFileName("F:\\result.txt");
        results.setPlugIn(null);
        results.setAppend(true);
        results.setBuffered(false);
        output.addOutputSynapse(results);
        
        /* All the layers must be activated invoking their method start;
         * the layers are implemented as Runnable objects, then they are
         * instanziated on separated threads.
         */
        input.start();
        middle.start();
        output.start();
        
        monitor.setTrainingPatterns(136); /* # of rows (patterns) contained in the input file */
        monitor.setTotCicles(5000); /* How many times the net must be trained on the input patterns */
        monitor.setLearning(true); /* The net must be trained */
        monitor.Go(); /* The net starts the training job */
        
//        try {
//    		FileOutputStream stream = new FileOutputStream("data/XORNet.snet");
//    		ObjectOutputStream out = new ObjectOutputStream(stream);
//    		out.writeObject(nnet); out.close();
//    		} catch (Exception excp) {
//    			excp.printStackTrace();
//    		}
//        
//        
    }

	public void convert() {
		
	}
	public void netStopped(NeuralNetEvent e) {

		
        System.out.println("Training finished");
        System.exit(0);
    }
    
    public void cicleTerminated(NeuralNetEvent e) {
    }
    
    public void netStarted(NeuralNetEvent e) {
        System.out.println("Training...");
    }
    
    public void errorChanged(NeuralNetEvent e) {
        Monitor mon = (Monitor)e.getSource();
        long c = mon.getCurrentCicle();
        long cl = c / 1000;
        /* We want print the results every 1000 cycles */
        if ((cl * 1000) == c)
            System.out.println(c + " cycles remaining - RMSE = " + mon.getGlobalError());
    }
    
    public void netStoppedError(NeuralNetEvent e,String error) {
    }
	
	
	
}
