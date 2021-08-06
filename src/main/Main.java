package main;

import Parsers.ParseFile;
import strashing.AIG;
import strashing.Node;
import strashing.NodeLib;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Main {

	public static final int AND = 1;
	public static final int OR = 2;

	public static void main(String[] args) {
		int M;   //maximum index M=I+L+A
		int I;   //input
		int L;   //latch
		int O;   //output
		int A;   //and gate, number of nodes

		/*parse the blif file*/
		ParseFile p= new ParseFile();
		p.paserFile("E:\\eclipse-workspace\\MiniTask1\\src\\0.blif");

		char[] InputNames=p.getInputName();
		char[] OutputNames=p.getOutputName();

		/*parse the primary input and intialize the library with it*/
		NodeLib LookUpLib = new NodeLib();
		LookUpLib.initLib(InputNames);

		/*construct the AIG-tree*/
		AIG aigTree = new AIG();
		String[] inputMinterm = p.inputMinterm;
		for(int j=1; j<InputNames.length; j++) {//traverse the content of the Minterm
			for (String s : inputMinterm) {//traverse every Minterm
				String keyofN1 = s.substring(0, j);//0th to j-1th character
				String keyofN2 = s.substring(j, j + 1);//jth character
				aigTree.generateAIG(LookUpLib, keyofN1, keyofN2, AND);
			}
		}

		/*construct the Or Gate in the Form of And-Inverter Gate*/
		int indexOfMintermLevel = LookUpLib.getNumOfLevels() - 1;
		HashMap<String, Node> mintermLevel = LookUpLib.Aig_NodeLib.get(indexOfMintermLevel);
		ArrayList<Node> mintermTable = new ArrayList<>();
		for (Map.Entry<String, Node> entry: mintermLevel.entrySet()) {
			mintermTable.add(entry.getValue());
		}

		Node tempNode = mintermTable.get(0);
		for(int i=1; i<mintermTable.size(); i++){
			Node n = mintermTable.get(i);
			tempNode = aigTree.Op_Or(LookUpLib, tempNode, n);
		}


		/*output the library in the console*/
		for(HashMap<String, Node> map: LookUpLib.Aig_NodeLib) {
			for(Map.Entry<String, Node> entry: map.entrySet()) {
				System.out.print(entry.getKey() + " ");
			}
			System.out.println();
			System.out.println("--------------");
		}

//		System.out.println(LookUpLib.getCommmonNode("AbC"));

		//******************************** init Writing ******************************************
		System.out.println(InputNames);

		I=InputNames.length;
		O=OutputNames.length;
		//****************************************************************************************

		//****************************************************************************************
		A=aigTree.calculate_AIGNode(LookUpLib);
		L=0;
		M=I+L+A;
		//****************************  Write  ***************************************************

		StringBuilder s=new StringBuilder();

		HashMap<Integer, Node> inputs = new HashMap<>();
		HashMap<Integer, Node> outputs = new HashMap<>();
		ArrayList<Node> ands;

		for (char inputName : InputNames) {
			Node n = LookUpLib.getNode(Character.toString(inputName));
			inputs.put(n.index, n);
		}

		outputs.put(tempNode.index, tempNode);

		ands=aigTree.flieNodes(LookUpLib);

		s.append(String.format("aag %d %d %d %d %d\n", M, I, L, O, A));
		for(Map.Entry<Integer, Node> entry: inputs.entrySet()){
			s.append(String.format("%d\n",entry.getKey()));
		}

		for (Map.Entry<Integer, Node> entry: outputs.entrySet()){
			s.append(String.format("%d\n",entry.getKey()));
		}

		for (Node node: ands){
			s.append(String.format(("%d %d %d\n"), node.index, node.leftChild.index, node.rightChild.index));
		}

		for(int i=0;i<InputNames.length;i++){
			s.append(String.format(("i%d %c\n"), i, InputNames[i]));
		}

		for(int i=0;i<OutputNames.length;i++){
			s.append(String.format(("o%d %c\n"), i, OutputNames[i]));
		}

		/*comment*/
		s.append("c\n");
		s.append("bliftoaig by our method\n");

		FileWriter fileWriter;
		try {
			fileWriter=new FileWriter("result.aag");
			fileWriter.write(String.valueOf(s));
			fileWriter.flush();
			fileWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
