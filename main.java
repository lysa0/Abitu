import java.io.*;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

public class main {
	public Arrays arr;
	public List<String> lst;
	public int max;
	public int[][] usersPriority;
	public String progr[];
	public char prf[];
	public char prs[];
	public char prt[];
	public String sites[];
	public static void main(String[] args) throws IOException{
		main m=new main();
		m.SearchProgrAndSites();
	}
	public void SearchProgrAndSites() throws IOException{
		URL url = new URL("http://cabinet.spbu.ru/Lists/1k_EntryLists/");
		InputStream in = url.openStream();
		BufferedReader br = new BufferedReader(new InputStreamReader(in));
		max=0;
		progr = new String[1000];
		prf = new char[1000];
		prs = new char[1000];
		prt = new char[1000];
		sites = new String[1000];
		//usersPriority = new int [500000][20];
		int uid=0;
		int count=0;	
		File myPath = new File("data");
		File myPath1 = new File("data/Дневная");
		File myPath2 = new File("data/Вечерняя");
		File myPath3 = new File("data/Дневная/Бюджет");
		File myPath4 = new File("data/Дневная/Договор");
		File myPath5 = new File("data/Вечерняя/Бюджет");
		File myPath6 = new File("data/Вечерняя/Договор");
		File myPath7 = new File("data/Дневная/Бюджет/Крым");
		File myPath8 = new File("data/Дневная/Договор/Крым");
		File myPath9 = new File("data/Дневная/Бюджет/Целевое");
		myPath.mkdir(); myPath.mkdirs();
		myPath1.mkdir(); myPath1.mkdirs();
		myPath2.mkdir(); myPath2.mkdirs();
		myPath3.mkdir(); myPath3.mkdirs();
		myPath4.mkdir(); myPath4.mkdirs();
		myPath5.mkdir(); myPath5.mkdirs();
		myPath6.mkdir(); myPath6.mkdirs();
		myPath7.mkdir(); myPath7.mkdirs();
		myPath8.mkdir(); myPath8.mkdirs();
		myPath9.mkdir(); myPath9.mkdirs();
		/*while (true) {
		  String line = br.readLine();
		  if (line != null) {
		  if (line.contains("Оригиналы"))
		  break;
		  } else
		  break;
		  }*/
		/*File mainFile = new File("main.html");
		  try {
		  if(!mainFile.exists()){
		  mainFile.createNewFile();
		  }
		  } catch(IOException e) {
		  throw new RuntimeException(e);
		  } 

		  PrintWriter mainOut = new PrintWriter(mainFile.getAbsoluteFile());
		 */
		while (true) {
			String line = br.readLine();
			System.out.print(line+"\n");
			if (line != null) {
				/*if (line.contains("<td"))
				  count++;
				  if (line.contains("</tr>"))
				  count = 0;
				  if (count == 2)
				  uid = Integer.valueOf(line.substring(17, line.length() - 5));*/
				if (line.contains("GetPerson")) {
					GetSites(uid, line.substring(line.indexOf("GetPerson") + 18, line.indexOf("GetPerson") + 54) + ".txt" + "\n");
				}
				/*if (count == 6) {
				  usersPriority[uid - 1600000][0] = (line.contains("Да") ? 1 : 0);
				  }*/
			}
			else
				break;
		}
		max-=1;
		GetProgr();
		/*for (int i=0; i<max; i++){
		  System.out.print(sites[i]+" "+progr[i]+"\n");
		  }*/
		for (int i=0; i<max; i++)
			CreateProgram(i);
	}
	public void GetSites(int uid, String str) throws IOException {
		String site;
		URL url = new URL("http://cabinet.spbu.ru/Lists/1k_EntryLists/data/" + str);
		InputStream in = url.openStream();
		BufferedReader br = new BufferedReader(new InputStreamReader(in));
		String line = br.readLine();
		String UUID = "";
		/*
			File txtFile = new File("data/"+str);
			try {
			if(!txtFile.exists()){
			txtFile.createNewFile();
			}
			} catch(IOException e) {
			throw new RuntimeException(e);
			} 
			PrintWriter txtOut = new PrintWriter(txtFile.getAbsoluteFile());*/
		//int priority=1;
		while (true) {
			if (line == null)
				break;
			//	txtOut.print(line);
			if (line.contains("программа")) {
				UUID=line.substring(line.indexOf("href=\"") + 6, line.indexOf("\">Просмотр")-42);
				site = "http://cabinet.spbu.ru/Lists/1k_EntryLists/"+UUID+".html";
				Conv();
				//	System.out.print(site);
				if (!(lst.contains(site))) {
					System.out.print(site);
					//CreateProgram(UUID);
					sites[max]=site;
					max++;
				}/*
					 usersPriority[uid-1600000][priority]=SearchProgr(site);
					 priority++;*/
			}
			line = br.readLine();
		}
		in.close();
	}
	public void CreateProgram(int num) throws IOException {
		String namep="data/";
		if (prf[num]=='d')
			namep+="Дневная/";
		else
			namep+="Вечерняя/";
		if (prs[num]=='b')
			namep+="Бюджет/";
		else
			namep+="Договор/";
		if (prt[num]=='c')
			namep+="Целевое/";
		if (prt[num]=='k')
			namep+="Крым/";
		namep+=progr[num];
		namep+=".html";
		File file = new File(namep);
		try {
			if(!file.exists()){
				file.createNewFile();
			}
		} catch(IOException e) {
			throw new RuntimeException(e);
		}
		PrintWriter out = new PrintWriter(file.getAbsoluteFile());
		//out.print(sites[num]+"\n");
		URL url = new URL(sites[num]);
		InputStream in = url.openStream();
		BufferedReader br = new BufferedReader(new InputStreamReader(in));
		//out.print(sites[num]);
		String line;
		while (true) {
			line = br.readLine();
			if (line == null)
				break;
			out.print(line);
		}
		out.close();

	}

	public void GetProgr() throws IOException {
		String name = "";
		for (int i=0; i<max; i++) {
			System.out.print(i);
			System.out.print("\n");
			URL url = new URL(sites[i]);
			InputStream in = url.openStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String line;
			while (true) {
				line = br.readLine();
				if (line.contains("Образовательная программа"))
					break;
				if (line == null)
					break;
			}
			name = line.substring(line.indexOf("программа") + 28);
			name = name.substring(0, name.length() - 7);
			while (true) {
				line = br.readLine();
				if (line.contains("Форма обучения"))
					break;
				if (line == null)
					break;
			}
			if (line.contains("очно"))
				prf[i]='v';
			else
				prf[i]='d';
			while (true) {
				line = br.readLine();
				if (line.contains("Основа обучения"))
					break;
				if (line == null)
					break;
			}
			if (line.contains("Госбюджетная"))
				prs[i]='b';
			else
				prs[i]='d';
			line = br.readLine();
			if (line.contains("цел"))
				prt[i]='c';
			if (line.contains("Крым"))
				prt[i]='k';
			if (ProgrCheck(name, i))
				name+="_1";
			progr[i]=name;
			if (ProgrCheck(name, i))
				name+="_1";
			progr[i]=name;

			if (ProgrCheck(name, i))
				name+="_1";
			progr[i]=name;

		}
	}
	void Conv(){
		lst = arr.asList(sites);
	}
	public boolean ProgrCheck(String str, int max){
		boolean b=false;
		for (int i=0; i<max; i++)
			if (progr[i].contains(str) && str.contains(progr[i]) && prf[max]==prf[i] && prs[max]==prs[i] && prt[max]==prt[i])
				b=true;
		return b;
	}
}
