package main;

import Parsers.ParseFile;
import strashing.AIG;
import strashing.Node;
import strashing.SubstruLib;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Main {

	public Main() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		int M;   //maximum index M=I+L+A
		int I;   //input
		int L;   //latch
		int O;   //output
		int A;   //and gate, number of nodes
		ParseFile p= new ParseFile();
		p.paserFile("D:\\TUD\\lls_pro\\src\\2.blif");

		Node firstMintermFromParser = new Node(p.inputMinterm[0]);
		SubstruLib LookUpLib = new SubstruLib();
		LookUpLib.initLib(firstMintermFromParser, LookUpLib.lib);

		for(int i=1; i<p.inputMinterm.length; i++) {
			String keyOfinputMinterm = p.inputMinterm[i];
			LookUpLib.generateAIG(keyOfinputMinterm);
		}
//
//
//
		for(HashMap<String, Node> map: LookUpLib.lib) {
			for(Map.Entry<String, Node> entry: map.entrySet()) {
				System.out.print(entry.getKey() + " ");
			}
			System.out.println();
			System.out.println("--------------");
		}

		AIG aig = new AIG();
		System.out.println(aig.calculate_AIGNode(LookUpLib.lib, LookUpLib.AIG_commonNode));

//		System.out.println(LookUpLib.getCommmonNode("AbC"));
		//******************************** init Writing ******************************************
		char[] InputNames=p.getInputName();
		System.out.println(InputNames);
		char[] OutputNames=p.getOutputName();

		I=InputNames.length;
		O=OutputNames.length;
		//****************************************************************************************

		//****************************************************************************************
		A=aig.calculate_AIGNode(LookUpLib.lib, LookUpLib.AIG_commonNode);
		L=0;
		M=I+L+A;
		//****************************  Write  ***************************************************
		try {
			BufferedWriter bw=new BufferedWriter(new FileWriter("Result.aig"));
			bw.write("aag"+" "+M+" "+I+" "+L+" "+O+" "+A);
			bw.newLine();
			int index=0;
			for(int i=0;i<InputNames.length;i++){
				if(Character.isUpperCase(InputNames[i])){	//uppercase:0, lowercase:1
					//String tmp=Integer.toBinaryString(index);
					//bw.write(index+"    "+InputNames[i]);
					bw.write(index);
					index=index+2;
				}else{
					index++;
					//String tmp=Integer.toBinaryString(index);
					bw.write(index+"    "+InputNames[i]);
					index++;
				}
				bw.newLine();
			}
			for (int j=0;j<OutputNames.length;j++){
				//String tmp=Integer.toBinaryString(index);
				bw.write(index+"    "+OutputNames[j]);
				bw.newLine();
			}
			//TODO: to print and gate names(Node names)
			/*
			to get and gate:
			for every node:
			father index, leftChild index, rightChild index    +       AND gate index
			* */
			//*******************************************************************
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
