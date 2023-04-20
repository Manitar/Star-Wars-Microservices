package bgu.spl.mics.application;


import bgu.spl.mics.MessageBusImpl;
import bgu.spl.mics.application.passiveObjects.Diary;
import bgu.spl.mics.application.passiveObjects.DiaryToJson;
import bgu.spl.mics.application.passiveObjects.Ewoks;
import bgu.spl.mics.application.services.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;


/** This is the Main class of the application. You should parse the input file,
 * create the different components of the application, and run the system.
 * In the end, you should output a JSON.
 */
public class Main {

	public static GsonAccess setGson(String inputPath) throws IOException {
		Gson gson = new Gson();
		Reader reader = new FileReader(inputPath);
		GsonAccess gsonAccess = gson.fromJson(reader, GsonAccess.class);
		return gsonAccess;

	}

	public static void main(String[] args) throws IOException {

		//Create Message Bus
		MessageBusImpl mBus = MessageBusImpl.getInstance();

		//Get the Gson Access class
		GsonAccess gsonAccess = setGson(args[0]);


		//Ewoks
		Ewoks ewoks = Ewoks.getInstance(gsonAccess.getEwoks());

		//Diary
		Diary diary = Diary.getInstance();

		//Create microservices
		LeiaMicroservice Leia = new LeiaMicroservice(gsonAccess.getAttacks());
		C3POMicroservice C3PO = new C3POMicroservice();
		HanSoloMicroservice HanSolo = new HanSoloMicroservice();
		R2D2Microservice R2D2 = new R2D2Microservice(gsonAccess.getR2D2());
		LandoMicroservice Lando = new LandoMicroservice(gsonAccess.getLando());

		//Create threads
		Thread tLeia = new Thread(Leia);
		Thread tC3PO = new Thread(C3PO);
		Thread tHanSolo = new Thread(HanSolo);
		Thread tR2D2 = new Thread(R2D2);
		Thread tLando = new Thread(Lando);

		//Run all threads
		tLeia.start();
		tC3PO.start();
		tHanSolo.start();
		tR2D2.start();
		tLando.start();

		try{
			tLeia.join();
			tC3PO.join();
			tHanSolo.join();
			tR2D2.join();
			tLando.join();

		}

		catch(InterruptedException e){e.printStackTrace();}
		DiaryToJson diaryToJson = new DiaryToJson(diary.getTotalAttacks(), diary.getHanSoloFinish(), diary.getC3POFinish(),
				diary.getR2D2Deactivate(), diary.getLeiaTerminate(), diary.getHanSoloTerminate(), diary.getC3POTerminate(),
				diary.getR2D2Terminate(), diary.getLandoTerminate());

		Gson gsonWriter = new GsonBuilder().setPrettyPrinting().create();
		FileWriter writer = new FileWriter(args[1]);
		gsonWriter.toJson(diaryToJson, writer);
		writer.flush();
		writer.close();



	}
}