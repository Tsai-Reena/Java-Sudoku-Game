import java.awt.*;
import java.awt.event.*;
import java.util.*;

/*生成隨機數獨流程：
  1.生成9*9空白數獨數組
  2.隨機往數獨數組填數
  3.DFS生成數獨標準解（即數獨數組81個格子都填滿數字）
  4.先挖n個空，對挖完空的數獨進行求解（全部）
  5.將所有解與標準解進行對比，將不一樣的地方記錄下。這些地方不允許被被挖空
*/

class MakeSDK {
	private int[][] Arr;//臨時數組
	private int [][]Sudoku;
	private int [][]Answer;//答案
	private int [][]Game;
	public static int answer[]=new int[82];

	public MakeSDK(){
		this.Arr=new int[9][9];
		this.Sudoku=new int[9][9];
		this.Answer=new int[9][9];
		rand();
		DFS(Arr,0,false);
		diger();
	}

	private void rand(){
		int t=0;
		int x,y,num;
		//先往數組裏面隨機丟t個數
		while(t<15){//t不宜多，否則運行起來耗費時間；t也不宜少，否則生成的遊戲看起來一點也不隨機
			x=new Random().nextInt(9);
			y=new Random().nextInt(9);
			num=new Random().nextInt(9)+1;
			if(Arr[y][x]==0){
				if(isTrue(Arr,x,y,num)==true){
					Arr[y][x]=num;++t;
				}
			}
		}
	}

	//判斷該數字填寫的地方是否符合數獨規則
	private boolean isTrue(int arr[][],int x,int y,int num){//數字橫座標；數字縱座標；數字數值
		//判斷中單元格（3*3）
		for(int i=(y/3)*3;i<(y/3+1)*3;++i){
			for(int j=(x/3)*3;j<(x/3+1)*3;++j){
				if(arr[i][j]==num){
					return false;
				}
			}
		}
		//判斷橫豎
		for(int i=0;i<9;++i){
			if(arr[i][x]==num || arr[y][i]==num){
				return false;
			}
		}
		return true;
	}

	//深度優先搜索尋找
	//絕對有很多種解法，但是我們只需要第一個解出來的
	private boolean flag=false;//判斷是否得出一個解
	int total=0;
	private void DFS(int arr[][],int n,boolean all){//arr是數獨數組，n是探索深度（一共81個格子，深度?81,n?0~80），是否要求全部解
		//n/9?格子的縱座標，n%9?格子的橫座標
		if(n<81){
			//如果已經求出了一種解，終止遞歸就行了，不用繼續求下去了
			if(flag==true && all==false){
				return;
			}
			if(arr[n/9][n%9]==0){
				for(int i=1;i<10;++i){
					if(isTrue(arr,n%9,n/9,i)==true){
						arr[n/9][n%9]=i;
						DFS(arr,n+1,all);
						arr[n/9][n%9]=0;
					}
				}
			}
			else{
				DFS(arr,n+1,all);
			}

		}
		else{
			if(all==false){
				flag=true;
				for(int i=0;i<9;++i){
					for(int j=0;j<9;++j){
						Sudoku[i][j]=arr[i][j];
						Answer[i][j]=arr[i][j];
					}
				}
			}
			else{
				++total;
				int k=1;
				for(int i=0;i<9;++i){
					for(int j=0;j<9;++j){
						if(arr[i][j]!=Answer[i][j]){
							Game[i][j]=Answer[i][j];
						}
						answer[k]=arr[i][j];
						k++;
					}
				}
			}
		}
	}


	//給數獨挖空
	//保證僅有一解
	private void diger(){
		int t=45;
		Game=new int[9][9];
		while(t>0){
			int x=new Random().nextInt(9);
			int y=new Random().nextInt(9);
			if(Sudoku[y][x]!=0){
				Sudoku[y][x]=0;--t;
			}
		}

		for(int i=0;i<9;++i){
			for(int j=0;j<9;++j){
				Game[i][j]=Sudoku[i][j];
			}
		}

		DFS(Sudoku,0,true);
	}

	//獲取最終數獨
	public int[][] getArr(){
		return this.Game;
	}

	//獲取數獨答案
	public int[][] getAnswer(){
		return this.Answer;
	}
}

public class Report_110816017 extends Frame implements ActionListener
{
	static Report_110816017 frm=new Report_110816017();
	static Button btn_easy=new Button("低級");
	static Button btn_mid=new Button("中級");
	static Button btn_hard=new Button("高級");
	static Button btn_start=new Button("START");
	static Button btn_quit=new Button("Quit");
	static Button btn_rule=new Button("遊戲說明");
	static Button btn_exit=new Button("我不玩了!!");
	static Button btn_continue=new Button("不小心按錯了，我要繼續遊戲");
	static Button btn_finish=new Button("完成數獨，進行批改");
	static Button btn_restart=new Button("這題太難了，我要重新選擇難度");
	static Button btn_restart_the_game=new Button("RESTART");
	static Button btn_end=new Button("END");
	static Button btn_back1=new Button("BACK");
	static Label lab_level=new Label("請選擇遊戲難度");
	static Label lab_ready=new Label("是否準備開始遊戲?");
	static Label lab_exit=new Label("是否結束遊戲?",Label.CENTER);
	static Label lab_win=new Label("CORRECT",Label.CENTER);
	static Label lab_lose=new Label("WRONG",Label.CENTER);
	static TextArea txa_rule=new TextArea("",500,250,TextArea.SCROLLBARS_VERTICAL_ONLY);
	static Font fnt=new Font("Serief",Font.BOLD,33);
	static Font fnt1=new Font("Serief",Font.BOLD,40);
	static Font fnt2=new Font("Serief",Font.PLAIN,25);
	static Font fnt3=new Font("Serief",Font.BOLD,60);
	static Dialog dlg=new Dialog(frm);
	static Dialog dlg1=new Dialog(frm);
	static Dialog dlg2=new Dialog(frm);

	static Label lab1=new Label("1",Label.CENTER);
	static Label lab2=new Label("2",Label.CENTER);
	static Label lab3=new Label("3",Label.CENTER);
	static Label lab4=new Label("4",Label.CENTER);
	static Label lab5=new Label("5",Label.CENTER);
	static Label lab6=new Label("6",Label.CENTER);
	static Label lab7=new Label("7",Label.CENTER);
	static Label lab8=new Label("8",Label.CENTER);
	static Label lab9=new Label("9",Label.CENTER);
	static Label lab10=new Label("10",Label.CENTER);
	static Label lab11=new Label("11",Label.CENTER);
	static Label lab12=new Label("12",Label.CENTER);
	static Label lab13=new Label("13",Label.CENTER);
	static Label lab14=new Label("14",Label.CENTER);
	static Label lab15=new Label("15",Label.CENTER);
	static Label lab16=new Label("16",Label.CENTER);
	static Label lab17=new Label("17",Label.CENTER);
	static Label lab18=new Label("18",Label.CENTER);
	static Label lab19=new Label("19",Label.CENTER);
	static Label lab20=new Label("20",Label.CENTER);
	static Label lab21=new Label("21",Label.CENTER);
	static Label lab22=new Label("22",Label.CENTER);
	static Label lab23=new Label("23",Label.CENTER);
	static Label lab24=new Label("24",Label.CENTER);
	static Label lab25=new Label("25",Label.CENTER);
	static Label lab26=new Label("26",Label.CENTER);
	static Label lab27=new Label("27",Label.CENTER);
	static Label lab28=new Label("28",Label.CENTER);
	static Label lab29=new Label("29",Label.CENTER);
	static Label lab30=new Label("30",Label.CENTER);
	static Label lab31=new Label("31",Label.CENTER);
	static Label lab32=new Label("32",Label.CENTER);
	static Label lab33=new Label("33",Label.CENTER);
	static Label lab34=new Label("34",Label.CENTER);
	static Label lab35=new Label("35",Label.CENTER);
	static Label lab36=new Label("36",Label.CENTER);
	static Label lab37=new Label("37",Label.CENTER);
	static Label lab38=new Label("38",Label.CENTER);
	static Label lab39=new Label("39",Label.CENTER);
	static Label lab40=new Label("40",Label.CENTER);
	static Label lab41=new Label("41",Label.CENTER);
	static Label lab42=new Label("42",Label.CENTER);
	static Label lab43=new Label("43",Label.CENTER);
	static Label lab44=new Label("44",Label.CENTER);
	static Label lab45=new Label("45",Label.CENTER);
	static Label lab46=new Label("46",Label.CENTER);
	static Label lab47=new Label("47",Label.CENTER);
	static Label lab48=new Label("48",Label.CENTER);
	static Label lab49=new Label("49",Label.CENTER);
	static Label lab50=new Label("50",Label.CENTER);
	static Label lab51=new Label("51",Label.CENTER);
	static Label lab52=new Label("52",Label.CENTER);
	static Label lab53=new Label("53",Label.CENTER);
	static Label lab54=new Label("54",Label.CENTER);
	static Label lab55=new Label("55",Label.CENTER);
	static Label lab56=new Label("56",Label.CENTER);
	static Label lab57=new Label("57",Label.CENTER);
	static Label lab58=new Label("58",Label.CENTER);
	static Label lab59=new Label("59",Label.CENTER);
	static Label lab60=new Label("60",Label.CENTER);
	static Label lab61=new Label("61",Label.CENTER);
	static Label lab62=new Label("62",Label.CENTER);
	static Label lab63=new Label("63",Label.CENTER);
	static Label lab64=new Label("64",Label.CENTER);
	static Label lab65=new Label("65",Label.CENTER);
	static Label lab66=new Label("66",Label.CENTER);
	static Label lab67=new Label("67",Label.CENTER);
	static Label lab68=new Label("68",Label.CENTER);
	static Label lab69=new Label("69",Label.CENTER);
	static Label lab70=new Label("70",Label.CENTER);
	static Label lab71=new Label("71",Label.CENTER);
	static Label lab72=new Label("72",Label.CENTER);
	static Label lab73=new Label("73",Label.CENTER);
	static Label lab74=new Label("74",Label.CENTER);
	static Label lab75=new Label("75",Label.CENTER);
	static Label lab76=new Label("76",Label.CENTER);
	static Label lab77=new Label("77",Label.CENTER);
	static Label lab78=new Label("78",Label.CENTER);
	static Label lab79=new Label("79",Label.CENTER);
	static Label lab80=new Label("80",Label.CENTER);
	static Label lab81=new Label("81",Label.CENTER);

	static Button choose_num1=new Button("1");
	static Button choose_num2=new Button("2");
	static Button choose_num3=new Button("3");
	static Button choose_num4=new Button("4");
	static Button choose_num5=new Button("5");
	static Button choose_num6=new Button("6");
	static Button choose_num7=new Button("7");
	static Button choose_num8=new Button("8");
	static Button choose_num9=new Button("9");

	static Button btn1=new Button("");
	static Button btn2=new Button("");
	static Button btn3=new Button("");
	static Button btn4=new Button("");
	static Button btn5=new Button("");
	static Button btn6=new Button("");
	static Button btn7=new Button("");
	static Button btn8=new Button("");
	static Button btn9=new Button("");
	static Button btn10=new Button("");
	static Button btn11=new Button("");
	static Button btn12=new Button("");
	static Button btn13=new Button("");
	static Button btn14=new Button("");
	static Button btn15=new Button("");
	static Button btn16=new Button("");
	static Button btn17=new Button("");
	static Button btn18=new Button("");
	static Button btn19=new Button("");
	static Button btn20=new Button("");
	static Button btn21=new Button("");
	static Button btn22=new Button("");
	static Button btn23=new Button("");
	static Button btn24=new Button("");
	static Button btn25=new Button("");
	static Button btn26=new Button("");
	static Button btn27=new Button("");
	static Button btn28=new Button("");
	static Button btn29=new Button("");
	static Button btn30=new Button("");
	static Button btn31=new Button("");
	static Button btn32=new Button("");
	static Button btn33=new Button("");
	static Button btn34=new Button("");
	static Button btn35=new Button("");
	static Button btn36=new Button("");
	static Button btn37=new Button("");
	static Button btn38=new Button("");
	static Button btn39=new Button("");
	static Button btn40=new Button("");
	static Button btn41=new Button("");
	static Button btn42=new Button("");
	static Button btn43=new Button("");
	static Button btn44=new Button("");
	static Button btn45=new Button("");
	static Button btn46=new Button("");
	static Button btn47=new Button("");
	static Button btn48=new Button("");
	static Button btn49=new Button("");
	static Button btn50=new Button("");
	static Button btn51=new Button("");
	static Button btn52=new Button("");
	static Button btn53=new Button("");
	static Button btn54=new Button("");
	static Button btn55=new Button("");
	static Button btn56=new Button("");
	static Button btn57=new Button("");
	static Button btn58=new Button("");
	static Button btn59=new Button("");
	static Button btn60=new Button("");
	static Button btn61=new Button("");
	static Button btn62=new Button("");
	static Button btn63=new Button("");
	static Button btn64=new Button("");
	static Button btn65=new Button("");
	static Button btn66=new Button("");
	static Button btn67=new Button("");
	static Button btn68=new Button("");
	static Button btn69=new Button("");
	static Button btn70=new Button("");
	static Button btn71=new Button("");
	static Button btn72=new Button("");
	static Button btn73=new Button("");
	static Button btn74=new Button("");
	static Button btn75=new Button("");
	static Button btn76=new Button("");
	static Button btn77=new Button("");
	static Button btn78=new Button("");
	static Button btn79=new Button("");
	static Button btn80=new Button("");
	static Button btn81=new Button("");

	static GridLayout Layout=new GridLayout(9,9,5,5);
	static GridLayout grid=new GridLayout(3,3,5,5);
	static MakeSDK game=new MakeSDK();

	public static void main(String args[])
	{
		frm.setTitle("Puzzle Game");
		frm.setSize(1000,500);
		frm.setBackground(Color.gray);
		frm.setLayout(null);

		dlg.setTitle("Start");
		dlg.setBounds(200,200,750,400);
		dlg.setBackground(Color.gray);
		dlg.setLayout(null);

		dlg1.setTitle("Choose");
		dlg1.setBounds(200,200,750,400);
		dlg1.setBackground(Color.white);
		dlg1.setLayout(grid);

		dlg2.setTitle("Choose");
		dlg2.setBounds(200,200,600,300);
		dlg2.setBackground(Color.white);
		dlg2.setLayout(null);

		lab_exit.setBounds(150,50,300,30);
		btn_exit.setBounds(150,210,300,30);//("我不玩了!!")
		btn_continue.setBounds(125,90,350,30);//("不小心按錯了，我要繼續遊戲");
		btn_finish.setBounds(150,130,300,30);//("完成數獨，進行批改");
		btn_restart.setBounds(100,170,400,30);//("這題太難了，我要重新選擇難度");
		btn_back1.setBounds(75,80,150,30);//BACK

		dlg2.add(lab_exit);
		dlg2.add(btn_continue);
		dlg2.add(btn_finish);
		dlg2.add(btn_restart);
		dlg2.add(btn_exit);

		dlg1.add(choose_num1);
		dlg1.add(choose_num2);
		dlg1.add(choose_num3);
		dlg1.add(choose_num4);
		dlg1.add(choose_num5);
		dlg1.add(choose_num6);
		dlg1.add(choose_num7);
		dlg1.add(choose_num8);
		dlg1.add(choose_num9);

		lab_level.setFont(fnt);
		lab_ready.setFont(fnt);
		btn_easy.setFont(fnt);
		btn_mid.setFont(fnt);
		btn_hard.setFont(fnt);
		btn_start.setFont(fnt);
		btn_quit.setFont(fnt);
		btn_rule.setFont(fnt);

		lab_exit.setFont(fnt2);
		btn_exit.setFont(fnt2);
		btn_continue.setFont(fnt2);
		btn_restart.setFont(fnt2);
		btn_finish.setFont(fnt2);
		txa_rule.setFont(new Font("Times New Roman",Font.PLAIN,32));
		btn_back1.setFont(fnt2);
		btn_restart_the_game.setFont(fnt2);
		btn_end.setFont(fnt2);

		lab_win.setFont(fnt3);
		lab_lose.setFont(fnt3);

		txa_rule.setEditable(false);

		lab_win.setBounds(120,30,300,150);
		lab_lose.setBounds(120,30,300,150);
		lab_level.setBounds(390,80,300,80);
		lab_ready.setBounds(200,80,400,60);
		btn_easy.setBounds(440,180,120,80);
		btn_mid.setBounds(440,280,120,80);
		btn_hard.setBounds(440,380,120,80);
		btn_start.setBounds(200,180,150,60);
		btn_quit.setBounds(370,180,150,60);
		btn_rule.setBounds(200,280,320,60);
		txa_rule.setBounds(75,130,600,200);
		btn_restart_the_game.setBounds(100,200,120,80);
		btn_end.setBounds(330,200,120,80);


		lab_level.setForeground(Color.white);
		lab_ready.setForeground(Color.white);
		btn_easy.setBackground(Color.green);
		btn_easy.setForeground(Color.black);
		btn_mid.setBackground(Color.blue);
		btn_mid.setForeground(Color.white);
		btn_hard.setBackground(Color.red);
		btn_hard.setForeground(Color.white);
		btn_start.setBackground(new Color(135,206,235));
		btn_start.setForeground(Color.black);

		frm.add(lab_level);
		frm.add(btn_easy);
		frm.add(btn_mid);
		frm.add(btn_hard);
		dlg.add(lab_ready);
		dlg.add(btn_start);
		dlg.add(btn_quit);
		dlg.add(btn_rule);

		setColor_label();
		setColor_button();
		setColor_choose_num();
		setWord_label();
		setWord_button();
		setWord_choose_num();

		txa_rule.setText("遊戲說明:\n    (a)電腦會先在拼圖內放入一些提示數字，玩家必須於空白處進行填空，使每一列、每一行和每個九宮格內均有1至9的數字，但不可以重複。");
		txa_rule.append("\n    (b)玩家在空白處填入數字，使九宮格中所有的數字均滿足上述規則，即完成作答。");
		txa_rule.append("\n    (c)若要繳交答案，或是重新選擇遊戲難度等，按下關閉視窗的按鍵。");

		btn_easy.addActionListener(frm);
		btn_mid.addActionListener(frm);
		btn_hard.addActionListener(frm);
		btn_start.addActionListener(frm);
		btn_rule.addActionListener(frm);
		btn_quit.addActionListener(frm);
		btn_back1.addActionListener(frm);
		btn_restart_the_game.addActionListener(frm);
		btn_end.addActionListener(frm);


		btn1.addActionListener(frm);
		btn2.addActionListener(frm);
		btn3.addActionListener(frm);
		btn4.addActionListener(frm);
		btn5.addActionListener(frm);
		btn6.addActionListener(frm);
		btn7.addActionListener(frm);
		btn8.addActionListener(frm);
		btn9.addActionListener(frm);
		btn10.addActionListener(frm);
		btn11.addActionListener(frm);
		btn12.addActionListener(frm);
		btn13.addActionListener(frm);
		btn14.addActionListener(frm);
		btn15.addActionListener(frm);
		btn16.addActionListener(frm);
		btn17.addActionListener(frm);
		btn18.addActionListener(frm);
		btn19.addActionListener(frm);
		btn20.addActionListener(frm);
		btn21.addActionListener(frm);
		btn22.addActionListener(frm);
		btn23.addActionListener(frm);
		btn24.addActionListener(frm);
		btn25.addActionListener(frm);
		btn26.addActionListener(frm);
		btn27.addActionListener(frm);
		btn28.addActionListener(frm);
		btn29.addActionListener(frm);
		btn30.addActionListener(frm);
		btn31.addActionListener(frm);
		btn32.addActionListener(frm);
		btn33.addActionListener(frm);
		btn34.addActionListener(frm);
		btn35.addActionListener(frm);
		btn36.addActionListener(frm);
		btn37.addActionListener(frm);
		btn38.addActionListener(frm);
		btn39.addActionListener(frm);
		btn40.addActionListener(frm);
		btn41.addActionListener(frm);
		btn42.addActionListener(frm);
		btn43.addActionListener(frm);
		btn44.addActionListener(frm);
		btn45.addActionListener(frm);
		btn46.addActionListener(frm);
		btn47.addActionListener(frm);
		btn48.addActionListener(frm);
		btn49.addActionListener(frm);
		btn50.addActionListener(frm);
		btn51.addActionListener(frm);
		btn52.addActionListener(frm);
		btn53.addActionListener(frm);
		btn54.addActionListener(frm);
		btn55.addActionListener(frm);
		btn56.addActionListener(frm);
		btn57.addActionListener(frm);
		btn58.addActionListener(frm);
		btn59.addActionListener(frm);
		btn60.addActionListener(frm);
		btn61.addActionListener(frm);
		btn62.addActionListener(frm);
		btn63.addActionListener(frm);
		btn64.addActionListener(frm);
		btn65.addActionListener(frm);
		btn66.addActionListener(frm);
		btn67.addActionListener(frm);
		btn68.addActionListener(frm);
		btn69.addActionListener(frm);
		btn70.addActionListener(frm);
		btn71.addActionListener(frm);
		btn72.addActionListener(frm);
		btn73.addActionListener(frm);
		btn74.addActionListener(frm);
		btn75.addActionListener(frm);
		btn76.addActionListener(frm);
		btn77.addActionListener(frm);
		btn78.addActionListener(frm);
		btn79.addActionListener(frm);
		btn80.addActionListener(frm);
		btn81.addActionListener(frm);

		choose_num1.addActionListener(frm);
		choose_num2.addActionListener(frm);
		choose_num3.addActionListener(frm);
		choose_num4.addActionListener(frm);
		choose_num5.addActionListener(frm);
		choose_num6.addActionListener(frm);
		choose_num7.addActionListener(frm);
		choose_num8.addActionListener(frm);
		choose_num9.addActionListener(frm);

		btn_finish.addActionListener(frm);
		btn_restart.addActionListener(frm);
		btn_exit.addActionListener(frm);
		btn_continue.addActionListener(frm);

		frm.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e){
				if(frm.getComponentCount()==4){
					System.out.println("結束遊戲!");
					System.exit(0);
				}
				else{
					dlg2.setVisible(true);
				}
			}
		});
		dlg.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e){
				dlg.setVisible(false);
			}
		});
		dlg1.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e){
				dlg1.setVisible(false);
			}
		});
		dlg2.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e){
				dlg2.setVisible(false);
			}
		});
		frm.setVisible(true);
	}

	boolean easy=false;
	boolean mid=false;
	boolean hard=false;

	int which_btn=0;

	public void actionPerformed(ActionEvent e)
	{
		Button btn=(Button)e.getSource();
		if(btn==btn_easy){//選擇簡單版
			easy=true;
			frm.setLayout(Layout);
			dlg.setVisible(true);
		}
		else if(btn==btn_mid){//選擇中難版
			mid=true;
			frm.setLayout(Layout);
			dlg.setVisible(true);
		}
		else if(btn==btn_hard){//選擇困難版
			hard=true;
			frm.setLayout(Layout);
			dlg.setVisible(true);
		}
		else if(btn==btn_start){//開始遊戲
			frm.removeAll();
			frm.setBackground(Color.black);
			frm.setLayout(Layout);
			dlg.setVisible(false);
			for(int i=1;i<=81;i++){//產生一個數獨解答，並放入answer陣列中
				setText_lab(i,game.answer[i]);//將解答放入lab中
			}
			if(easy==true){
				easy=false;
				add_easy();
			}
			else if(mid==true){
				mid=false;
				add_mid();
			}
			else if(hard==true){
				hard=false;
				add_hard();
			}
		}
		else if(btn==btn_rule){
			dlg.removeAll();
			dlg.add(txa_rule);
			dlg.add(btn_back1);
		}
		else if(btn==btn_quit){
			System.out.println("結束遊戲!");
			System.exit(0);
		}
		else if(btn==btn_back1){
			dlg.removeAll();
			dlg.add(lab_ready);
			dlg.add(btn_start);
			dlg.add(btn_quit);
			dlg.add(btn_rule);
		}
		else if(btn==choose_num1){
			setLabel_btn(which_btn,1);
			which_btn=0;
			dlg1.setVisible(false);
		}
		else if(btn==choose_num2){
			setLabel_btn(which_btn,2);
			which_btn=0;
			dlg1.setVisible(false);
		}
		else if(btn==choose_num3){
			setLabel_btn(which_btn,3);
			which_btn=0;
			dlg1.setVisible(false);
		}
		else if(btn==choose_num4){
			setLabel_btn(which_btn,4);
			which_btn=0;
			dlg1.setVisible(false);
		}
		else if(btn==choose_num5){
			setLabel_btn(which_btn,5);
			which_btn=0;
			dlg1.setVisible(false);
		}
		else if(btn==choose_num6){
			setLabel_btn(which_btn,6);
			which_btn=0;
			dlg1.setVisible(false);
		}
		else if(btn==choose_num7){
			setLabel_btn(which_btn,7);
			which_btn=0;
			dlg1.setVisible(false);
		}
		else if(btn==choose_num8){
			setLabel_btn(which_btn,8);
			which_btn=0;
			dlg1.setVisible(false);
		}
		else if(btn==choose_num9){
			setLabel_btn(which_btn,9);
			which_btn=0;
			dlg1.setVisible(false);
		}
		else if(btn==btn_continue){
			dlg2.setVisible(false);
		}
		else if(btn==btn_restart){
			for(int i=1;i<=81;i++){
				clearLabel_btn(i);
			}
			dlg.setVisible(false);
			dlg1.setVisible(false);
			dlg2.setVisible(false);
			frm.removeAll();
			frm.setBackground(Color.gray);
			frm.add(lab_level);
			frm.add(btn_easy);
			frm.add(btn_mid);
			frm.add(btn_hard);

			dlg.removeAll();
			dlg.add(lab_ready);
			dlg.add(btn_start);
			dlg.add(btn_quit);
			dlg.add(btn_rule);

			dlg2.removeAll();
			dlg2.add(lab_exit);
			dlg2.add(btn_continue);
			dlg2.add(btn_finish);
			dlg2.add(btn_restart);
			dlg2.add(btn_exit);

			dlg1.removeAll();
			dlg1.add(choose_num1);
			dlg1.add(choose_num2);
			dlg1.add(choose_num3);
			dlg1.add(choose_num4);
			dlg1.add(choose_num5);
			dlg1.add(choose_num6);
			dlg1.add(choose_num7);
			dlg1.add(choose_num8);
			dlg1.add(choose_num9);
		}
		else if(btn==btn_restart_the_game){
			for(int i=1;i<=81;i++){
				clearLabel_btn(i);
			}
			dlg.setVisible(false);
			dlg1.setVisible(false);
			dlg2.setVisible(false);
			frm.removeAll();
			frm.setBackground(Color.gray);
			frm.add(lab_level);
			frm.add(btn_easy);
			frm.add(btn_mid);
			frm.add(btn_hard);

			dlg.removeAll();
			dlg.add(lab_ready);
			dlg.add(btn_start);
			dlg.add(btn_quit);
			dlg.add(btn_rule);

			dlg2.removeAll();
			dlg2.add(lab_exit);
			dlg2.add(btn_continue);
			dlg2.add(btn_finish);
			dlg2.add(btn_restart);
			dlg2.add(btn_exit);

			dlg1.removeAll();
			dlg1.add(choose_num1);
			dlg1.add(choose_num2);
			dlg1.add(choose_num3);
			dlg1.add(choose_num4);
			dlg1.add(choose_num5);
			dlg1.add(choose_num6);
			dlg1.add(choose_num7);
			dlg1.add(choose_num8);
			dlg1.add(choose_num9);
		}
		else if(btn==btn_end){
			System.out.println("結束遊戲!");
			System.exit(0);
		}
		else if(btn==btn_finish){
			if(correct()==true){
				dlg2.removeAll();
				dlg2.setLayout(null);
				dlg2.add(lab_win);
			}
			else{
				dlg2.removeAll();
				dlg2.setLayout(null);
				dlg2.add(lab_lose);
			}
			dlg2.add(btn_restart_the_game);
			dlg2.add(btn_end);
		}
		else if(btn==btn_exit){
			System.out.println("結束遊戲!");
			dlg2.setVisible(false);
			System.exit(0);
		}
		else{
			dlg1.setVisible(true);
			if(btn==btn1){
				clearLabel_btn(1);
				which_btn=1;
			}
			else if(btn==btn2){
				clearLabel_btn(2);
				which_btn=2;
			}
			else if(btn==btn3){
				clearLabel_btn(3);
				which_btn=3;
			}
			else if(btn==btn4){
				clearLabel_btn(4);
				which_btn=4;
			}
			else if(btn==btn5){
				clearLabel_btn(5);
				which_btn=5;
			}
			else if(btn==btn6){
				clearLabel_btn(6);
				which_btn=6;
			}
			else if(btn==btn7){
				clearLabel_btn(7);
				which_btn=7;
			}
			else if(btn==btn8){
				clearLabel_btn(8);
				which_btn=8;
			}
			else if(btn==btn9){
				clearLabel_btn(9);
				which_btn=9;
			}
			else if(btn==btn10){
				clearLabel_btn(10);
				which_btn=10;
			}
			else if(btn==btn11){
				clearLabel_btn(11);
				which_btn=11;
			}
			else if(btn==btn12){
				clearLabel_btn(12);
				which_btn=12;
			}
			else if(btn==btn13){
				clearLabel_btn(13);
				which_btn=13;
			}
			else if(btn==btn14){
				clearLabel_btn(14);
				which_btn=14;
			}
			else if(btn==btn15){
				clearLabel_btn(15);
				which_btn=15;
			}
			else if(btn==btn16){
				clearLabel_btn(16);
				which_btn=16;
			}
			else if(btn==btn17){
				clearLabel_btn(17);
				which_btn=17;
			}
			else if(btn==btn18){
				clearLabel_btn(18);
				which_btn=18;
			}
			else if(btn==btn19){
				clearLabel_btn(19);
				which_btn=19;
			}
			else if(btn==btn20){
				clearLabel_btn(20);
				which_btn=20;
			}
			else if(btn==btn21){
				clearLabel_btn(21);
				which_btn=21;
			}
			else if(btn==btn22){
				clearLabel_btn(22);
				which_btn=22;
			}
			else if(btn==btn23){
				clearLabel_btn(23);
				which_btn=23;
			}
			else if(btn==btn24){
				clearLabel_btn(24);
				which_btn=24;
			}
			else if(btn==btn25){
				clearLabel_btn(25);
				which_btn=25;
			}
			else if(btn==btn26){
				clearLabel_btn(26);
				which_btn=26;
			}
			else if(btn==btn27){
				clearLabel_btn(27);
				which_btn=27;
			}
			else if(btn==btn28){
				clearLabel_btn(28);
				which_btn=28;
			}
			else if(btn==btn29){
				clearLabel_btn(29);
				which_btn=29;
			}
			else if(btn==btn30){
				clearLabel_btn(30);
				which_btn=30;
			}
			else if(btn==btn31){
				clearLabel_btn(31);
				which_btn=31;
			}
			else if(btn==btn32){
				clearLabel_btn(32);
				which_btn=32;
			}
			else if(btn==btn33){
				clearLabel_btn(33);
				which_btn=33;
			}
			else if(btn==btn34){
				clearLabel_btn(34);
				which_btn=34;
			}
			else if(btn==btn35){
				clearLabel_btn(35);
				which_btn=35;
			}
			else if(btn==btn36){
				clearLabel_btn(36);
				which_btn=36;
			}
			else if(btn==btn37){
				clearLabel_btn(37);
				which_btn=37;
			}
			else if(btn==btn38){
				clearLabel_btn(38);
				which_btn=38;
			}
			else if(btn==btn39){
				clearLabel_btn(39);
				which_btn=39;
			}
			else if(btn==btn40){
				clearLabel_btn(40);
				which_btn=40;
			}
			else if(btn==btn41){
				clearLabel_btn(41);
				which_btn=41;
			}
			else if(btn==btn42){
				clearLabel_btn(42);
				which_btn=42;
			}
			else if(btn==btn43){
				clearLabel_btn(43);
				which_btn=43;
			}
			else if(btn==btn44){
				clearLabel_btn(44);
				which_btn=44;
			}
			else if(btn==btn45){
				clearLabel_btn(45);
				which_btn=45;
			}
			else if(btn==btn46){
				clearLabel_btn(46);
				which_btn=46;
			}
			else if(btn==btn47){
				clearLabel_btn(47);
				which_btn=47;
			}
			else if(btn==btn48){
				clearLabel_btn(48);
				which_btn=48;
			}
			else if(btn==btn49){
				clearLabel_btn(49);
				which_btn=49;
			}
			else if(btn==btn50){
				clearLabel_btn(50);
				which_btn=50;
			}
			else if(btn==btn51){
				clearLabel_btn(51);
				which_btn=51;
			}
			else if(btn==btn52){
				clearLabel_btn(52);
				which_btn=52;
			}
			else if(btn==btn53){
				clearLabel_btn(53);
				which_btn=53;
			}
			else if(btn==btn54){
				clearLabel_btn(54);
				which_btn=54;
			}
			else if(btn==btn55){
				clearLabel_btn(55);
				which_btn=55;
			}
			else if(btn==btn56){
				clearLabel_btn(56);
				which_btn=56;
			}
			else if(btn==btn57){
				clearLabel_btn(57);
				which_btn=57;
			}
			else if(btn==btn58){
				clearLabel_btn(58);
				which_btn=58;
			}
			else if(btn==btn59){
				clearLabel_btn(59);
				which_btn=59;
			}
			else if(btn==btn60){
				clearLabel_btn(60);
				which_btn=60;
			}
			else if(btn==btn61){
				clearLabel_btn(61);
				which_btn=61;
			}
			else if(btn==btn62){
				clearLabel_btn(62);
				which_btn=62;
			}
			else if(btn==btn63){
				clearLabel_btn(63);
				which_btn=63;
			}
			else if(btn==btn64){
				clearLabel_btn(64);
				which_btn=64;
			}
			else if(btn==btn65){
				clearLabel_btn(65);
				which_btn=65;
			}
			else if(btn==btn66){
				clearLabel_btn(66);
				which_btn=66;
			}
			else if(btn==btn67){
				clearLabel_btn(67);
				which_btn=67;
			}
			else if(btn==btn68){
				clearLabel_btn(68);
				which_btn=68;
			}
			else if(btn==btn69){
				clearLabel_btn(69);
				which_btn=69;
			}
			else if(btn==btn70){
				clearLabel_btn(70);
				which_btn=70;
			}
			else if(btn==btn71){
				clearLabel_btn(71);
				which_btn=71;
			}
			else if(btn==btn72){
				clearLabel_btn(72);
				which_btn=72;
			}
			else if(btn==btn73){
				clearLabel_btn(73);
				which_btn=73;
			}
			else if(btn==btn74){
				clearLabel_btn(74);
				which_btn=74;
			}
			else if(btn==btn75){
				clearLabel_btn(75);
				which_btn=75;
			}
			else if(btn==btn76){
				clearLabel_btn(76);
				which_btn=76;
			}
			else if(btn==btn77){
				clearLabel_btn(77);
				which_btn=77;
			}
			else if(btn==btn78){
				clearLabel_btn(78);
				which_btn=78;
			}
			else if(btn==btn79){
				clearLabel_btn(79);
				which_btn=79;
			}
			else if(btn==btn80){
				clearLabel_btn(80);
				which_btn=80;
			}
			else if(btn==btn81){
				clearLabel_btn(81);
				which_btn=81;
			}
		}
	}

	public boolean correct(){
		boolean cor=true;
		for(int i=1;i<=81;i++){
			if(getLabel_btn(i).equals(game.answer[i])==true){//correct
				cor=true;
			}
			else{//wrong
				cor=false;
				break;
			}
		}
		return cor;
	}

	int easy1[]=new int[33];
	int easy2[]=new int[33];
	int easy3[]=new int[33];

	int mid1[]=new int[31];
	int mid2[]=new int[30];
	int mid3[]=new int[31];

	int hard1[]=new int[25];
	int hard2[]=new int[24];
	int hard3[]=new int[28];

	public void add_easy(){//將btn與lab依序加入frm中
		int n=((int)(10*Math.random()))%3;
		if(n==0){
			n=3;
		}
		template();
		if(n==1){//template:easy1
			for(int i=1;i<=81;i++){
				add_btn(i);
				for(int j=1;j<=32;j++){
					if(easy1[j]==i){
						remove_btn(i);
						add_lab(i);
						setLabel_btn(i,game.answer[i]);//將解答放入btn中
					}
				}
			}
		}
		else if(n==2){//template:easy2
			for(int i=1;i<=81;i++){
				add_btn(i);
				for(int j=1;j<=32;j++){
					if(easy2[j]==i){
						remove_btn(i);
						clearLabel_btn(i);
						add_lab(i);
						setLabel_btn(i,game.answer[i]);//將解答放入btn中
					}
				}
			}
		}
		else if(n==3){//template:easy3
			for(int i=1;i<=81;i++){
				add_btn(i);
				for(int j=1;j<=32;j++){
					if(easy3[j]==i){
						remove_btn(i);
						clearLabel_btn(i);
						add_lab(i);
						setLabel_btn(i,game.answer[i]);//將解答放入btn中
					}
				}
			}
		}
	}

	public void add_mid(){//將btn與lab依序加入frm中
		int n=((int)(10*Math.random()))%3;
		if(n==0){
			n=3;
		}
		template();
		if(n==1){//template:easy1
			for(int i=1;i<=81;i++){
				add_btn(i);
				for(int j=1;j<=30;j++){
					if(mid1[j]==i){
						remove_btn(i);
						clearLabel_btn(i);
						add_lab(i);
						setLabel_btn(i,game.answer[i]);//將解答放入btn中
					}
				}
			}
		}
		else if(n==2){//template:easy2
			for(int i=1;i<=81;i++){
				add_btn(i);
				for(int j=1;j<=29;j++){
					if(mid2[j]==i){
						remove_btn(i);
						clearLabel_btn(i);
						add_lab(i);
						setLabel_btn(i,game.answer[i]);//將解答放入btn中
					}
				}
			}
		}
		else if(n==3){//template:easy3
			for(int i=1;i<=81;i++){
				add_btn(i);
				for(int j=1;j<=30;j++){
					if(mid3[j]==i){
						remove_btn(i);
						clearLabel_btn(i);
						add_lab(i);
						setLabel_btn(i,game.answer[i]);//將解答放入btn中
					}
				}
			}
		}
	}

	public void add_hard(){//將btn與lab依序加入frm中
		int n=((int)(10*Math.random()))%3;
		if(n==0){
			n=3;
		}
		template();
		if(n==1){//template:easy1
			for(int i=1;i<=81;i++){
				add_btn(i);
				for(int j=1;j<=24;j++){
					if(hard1[j]==i){
						remove_btn(i);
						clearLabel_btn(i);
						add_lab(i);
						setLabel_btn(i,game.answer[i]);//將解答放入btn中
					}
				}
			}
		}
		else if(n==2){//template:easy2
			for(int i=1;i<=81;i++){
				add_btn(i);
				for(int j=1;j<=23;j++){
					if(hard2[j]==i){
						remove_btn(i);
						clearLabel_btn(i);
						add_lab(i);
						setLabel_btn(i,game.answer[i]);//將解答放入btn中
					}
				}
			}
		}
		else if(n==3){//template:easy3
			for(int i=1;i<=81;i++){
				add_btn(i);
				for(int j=1;j<=27;j++){
					if(hard3[j]==i){
						remove_btn(i);
						clearLabel_btn(i);
						add_lab(i);
						setLabel_btn(i,game.answer[i]);//將解答放入btn中
					}
				}
			}
		}
	}

	public String getLabel_btn(int n){
		String ans="";
		if(n==1){
			ans=btn1.getLabel();
		}
		else if(n==2){
			ans=btn2.getLabel();
		}
		else if(n==3){
			ans=btn3.getLabel();
		}
		else if(n==4){
			ans=btn4.getLabel();
		}
		else if(n==5){
			ans=btn5.getLabel();
		}
		else if(n==6){
			ans=btn6.getLabel();
		}
		else if(n==7){
			ans=btn7.getLabel();
		}
		else if(n==8){
			ans=btn8.getLabel();
		}
		else if(n==9){
			ans=btn9.getLabel();
		}
		else if(n==10){
			ans=btn10.getLabel();
		}
		else if(n==11){
			ans=btn11.getLabel();
		}
		else if(n==12){
			ans=btn12.getLabel();
		}
		else if(n==13){
			ans=btn13.getLabel();
		}
		else if(n==14){
			ans=btn14.getLabel();
		}
		else if(n==15){
			ans=btn15.getLabel();
		}
		else if(n==16){
			ans=btn16.getLabel();
		}
		else if(n==17){
			ans=btn17.getLabel();
		}
		else if(n==18){
			ans=btn18.getLabel();
		}
		else if(n==19){
			ans=btn19.getLabel();
		}
		else if(n==20){
			ans=btn20.getLabel();
		}
		else if(n==21){
			ans=btn21.getLabel();
		}
		else if(n==22){
			ans=btn22.getLabel();
		}
		else if(n==23){
			ans=btn23.getLabel();
		}
		else if(n==24){
			ans=btn24.getLabel();
		}
		else if(n==25){
			ans=btn25.getLabel();
		}
		else if(n==26){
			ans=btn26.getLabel();
		}
		else if(n==27){
			ans=btn27.getLabel();
		}
		else if(n==28){
			ans=btn28.getLabel();
		}
		else if(n==29){
			ans=btn29.getLabel();
		}
		else if(n==30){
			ans=btn30.getLabel();
		}
		else if(n==31){
			ans=btn31.getLabel();
		}
		else if(n==32){
			ans=btn32.getLabel();
		}
		else if(n==33){
			ans=btn33.getLabel();
		}
		else if(n==34){
			ans=btn34.getLabel();
		}
		else if(n==35){
			ans=btn35.getLabel();
		}
		else if(n==36){
			ans=btn36.getLabel();
		}
		else if(n==37){
			ans=btn37.getLabel();
		}
		else if(n==38){
			ans=btn38.getLabel();
		}
		else if(n==39){
			ans=btn39.getLabel();
		}
		else if(n==40){
			ans=btn40.getLabel();
		}
		else if(n==41){
			ans=btn41.getLabel();
		}
		else if(n==42){
			ans=btn42.getLabel();
		}
		else if(n==43){
			ans=btn43.getLabel();
		}
		else if(n==44){
			ans=btn44.getLabel();
		}
		else if(n==45){
			ans=btn45.getLabel();
		}
		else if(n==46){
			ans=btn46.getLabel();
		}
		else if(n==47){
			ans=btn47.getLabel();
		}
		else if(n==48){
			ans=btn48.getLabel();
		}
		else if(n==49){
			ans=btn49.getLabel();
		}
		else if(n==50){
			ans=btn50.getLabel();
		}
		else if(n==51){
			ans=btn51.getLabel();
		}
		else if(n==52){
			ans=btn52.getLabel();
		}
		else if(n==53){
			ans=btn53.getLabel();
		}
		else if(n==54){
			ans=btn54.getLabel();
		}
		else if(n==55){
			ans=btn55.getLabel();
		}
		else if(n==56){
			ans=btn56.getLabel();
		}
		else if(n==57){
			ans=btn57.getLabel();
		}
		else if(n==58){
			ans=btn58.getLabel();
		}
		else if(n==59){
			ans=btn59.getLabel();
		}
		else if(n==60){
			ans=btn60.getLabel();
		}
		else if(n==61){
			ans=btn61.getLabel();
		}
		else if(n==62){
			ans=btn62.getLabel();
		}
		else if(n==63){
			ans=btn63.getLabel();
		}
		else if(n==64){
			ans=btn64.getLabel();
		}
		else if(n==65){
			ans=btn65.getLabel();
		}
		else if(n==66){
			ans=btn66.getLabel();
		}
		else if(n==67){
			ans=btn67.getLabel();
		}
		else if(n==68){
			ans=btn68.getLabel();
		}
		else if(n==69){
			ans=btn69.getLabel();
		}
		else if(n==70){
			ans=btn70.getLabel();
		}
		else if(n==71){
			ans=btn71.getLabel();
		}
		else if(n==72){
			ans=btn72.getLabel();
		}
		else if(n==73){
			ans=btn73.getLabel();
		}
		else if(n==74){
			ans=btn74.getLabel();
		}
		else if(n==75){
			ans=btn75.getLabel();
		}
		else if(n==76){
			ans=btn76.getLabel();
		}
		else if(n==77){
			ans=btn77.getLabel();
		}
		else if(n==78){
			ans=btn78.getLabel();
		}
		else if(n==79){
			ans=btn79.getLabel();
		}
		else if(n==80){
			ans=btn80.getLabel();
		}
		else if(n==81){
			ans=btn81.getLabel();
		}
		return ans;
	}

	public void setLabel_btn(int n,int m){
		if(n==1){
			btn1.setLabel(Integer.toString(m));
		}
		else if(n==2){
			btn2.setLabel(Integer.toString(m));
		}
		else if(n==3){
			btn3.setLabel(Integer.toString(m));
		}
		else if(n==4){
			btn4.setLabel(Integer.toString(m));
		}
		else if(n==5){
			btn5.setLabel(Integer.toString(m));
		}
		else if(n==6){
			btn6.setLabel(Integer.toString(m));
		}
		else if(n==7){
			btn7.setLabel(Integer.toString(m));
		}
		else if(n==8){
			btn8.setLabel(Integer.toString(m));
		}
		else if(n==9){
			btn9.setLabel(Integer.toString(m));
		}
		else if(n==10){
			btn10.setLabel(Integer.toString(m));
		}
		else if(n==11){
			btn11.setLabel(Integer.toString(m));
		}
		else if(n==12){
			btn12.setLabel(Integer.toString(m));
		}
		else if(n==13){
			btn13.setLabel(Integer.toString(m));
		}
		else if(n==14){
			btn14.setLabel(Integer.toString(m));
		}
		else if(n==15){
			btn15.setLabel(Integer.toString(m));
		}
		else if(n==16){
			btn16.setLabel(Integer.toString(m));
		}
		else if(n==17){
			btn17.setLabel(Integer.toString(m));
		}
		else if(n==18){
			btn18.setLabel(Integer.toString(m));
		}
		else if(n==19){
			btn19.setLabel(Integer.toString(m));
		}
		else if(n==20){
			btn20.setLabel(Integer.toString(m));
		}
		else if(n==21){
			btn21.setLabel(Integer.toString(m));
		}
		else if(n==22){
			btn22.setLabel(Integer.toString(m));
		}
		else if(n==23){
			btn23.setLabel(Integer.toString(m));
		}
		else if(n==24){
			btn24.setLabel(Integer.toString(m));
		}
		else if(n==25){
			btn25.setLabel(Integer.toString(m));
		}
		else if(n==26){
			btn26.setLabel(Integer.toString(m));
		}
		else if(n==27){
			btn27.setLabel(Integer.toString(m));
		}
		else if(n==28){
			btn28.setLabel(Integer.toString(m));
		}
		else if(n==29){
			btn29.setLabel(Integer.toString(m));
		}
		else if(n==30){
			btn30.setLabel(Integer.toString(m));
		}
		else if(n==31){
			btn31.setLabel(Integer.toString(m));
		}
		else if(n==32){
			btn32.setLabel(Integer.toString(m));
		}
		else if(n==33){
			btn33.setLabel(Integer.toString(m));
		}
		else if(n==34){
			btn34.setLabel(Integer.toString(m));
		}
		else if(n==35){
			btn35.setLabel(Integer.toString(m));
		}
		else if(n==36){
			btn36.setLabel(Integer.toString(m));
		}
		else if(n==37){
			btn37.setLabel(Integer.toString(m));
		}
		else if(n==38){
			btn38.setLabel(Integer.toString(m));
		}
		else if(n==39){
			btn39.setLabel(Integer.toString(m));
		}
		else if(n==40){
			btn40.setLabel(Integer.toString(m));
		}
		else if(n==41){
			btn41.setLabel(Integer.toString(m));
		}
		else if(n==42){
			btn42.setLabel(Integer.toString(m));
		}
		else if(n==43){
			btn43.setLabel(Integer.toString(m));
		}
		else if(n==44){
			btn44.setLabel(Integer.toString(m));
		}
		else if(n==45){
			btn45.setLabel(Integer.toString(m));
		}
		else if(n==46){
			btn46.setLabel(Integer.toString(m));
		}
		else if(n==47){
			btn47.setLabel(Integer.toString(m));
		}
		else if(n==48){
			btn48.setLabel(Integer.toString(m));
		}
		else if(n==49){
			btn49.setLabel(Integer.toString(m));
		}
		else if(n==50){
			btn50.setLabel(Integer.toString(m));
		}
		else if(n==51){
			btn51.setLabel(Integer.toString(m));
		}
		else if(n==52){
			btn52.setLabel(Integer.toString(m));
		}
		else if(n==53){
			btn53.setLabel(Integer.toString(m));
		}
		else if(n==54){
			btn54.setLabel(Integer.toString(m));
		}
		else if(n==55){
			btn55.setLabel(Integer.toString(m));
		}
		else if(n==56){
			btn56.setLabel(Integer.toString(m));
		}
		else if(n==57){
			btn57.setLabel(Integer.toString(m));
		}
		else if(n==58){
			btn58.setLabel(Integer.toString(m));
		}
		else if(n==59){
			btn59.setLabel(Integer.toString(m));
		}
		else if(n==60){
			btn60.setLabel(Integer.toString(m));
		}
		else if(n==61){
			btn61.setLabel(Integer.toString(m));
		}
		else if(n==62){
			btn62.setLabel(Integer.toString(m));
		}
		else if(n==63){
			btn63.setLabel(Integer.toString(m));
		}
		else if(n==64){
			btn64.setLabel(Integer.toString(m));
		}
		else if(n==65){
			btn65.setLabel(Integer.toString(m));
		}
		else if(n==66){
			btn66.setLabel(Integer.toString(m));
		}
		else if(n==67){
			btn67.setLabel(Integer.toString(m));
		}
		else if(n==68){
			btn68.setLabel(Integer.toString(m));
		}
		else if(n==69){
			btn69.setLabel(Integer.toString(m));
		}
		else if(n==70){
			btn70.setLabel(Integer.toString(m));
		}
		else if(n==71){
			btn71.setLabel(Integer.toString(m));
		}
		else if(n==72){
			btn72.setLabel(Integer.toString(m));
		}
		else if(n==73){
			btn73.setLabel(Integer.toString(m));
		}
		else if(n==74){
			btn74.setLabel(Integer.toString(m));
		}
		else if(n==75){
			btn75.setLabel(Integer.toString(m));
		}
		else if(n==76){
			btn76.setLabel(Integer.toString(m));
		}
		else if(n==77){
			btn77.setLabel(Integer.toString(m));
		}
		else if(n==78){
			btn78.setLabel(Integer.toString(m));
		}
		else if(n==79){
			btn79.setLabel(Integer.toString(m));
		}
		else if(n==80){
			btn80.setLabel(Integer.toString(m));
		}
		else if(n==81){
			btn81.setLabel(Integer.toString(m));
		}
	}

	public void clearLabel_btn(int n){
		if(n==1){
			btn1.setLabel("");
		}
		else if(n==2){
			btn2.setLabel("");
		}
		else if(n==3){
			btn3.setLabel("");
		}
		else if(n==4){
			btn4.setLabel("");
		}
		else if(n==5){
			btn5.setLabel("");
		}
		else if(n==6){
			btn6.setLabel("");
		}
		else if(n==7){
			btn7.setLabel("");
		}
		else if(n==8){
			btn8.setLabel("");
		}
		else if(n==9){
			btn9.setLabel("");
		}
		else if(n==10){
			btn10.setLabel("");
		}
		else if(n==11){
			btn11.setLabel("");
		}
		else if(n==12){
			btn12.setLabel("");
		}
		else if(n==13){
			btn13.setLabel("");
		}
		else if(n==14){
			btn14.setLabel("");
		}
		else if(n==15){
			btn15.setLabel("");
		}
		else if(n==16){
			btn16.setLabel("");
		}
		else if(n==17){
			btn17.setLabel("");
		}
		else if(n==18){
			btn18.setLabel("");
		}
		else if(n==19){
			btn19.setLabel("");
		}
		else if(n==20){
			btn20.setLabel("");
		}
		else if(n==21){
			btn21.setLabel("");
		}
		else if(n==22){
			btn22.setLabel("");
		}
		else if(n==23){
			btn23.setLabel("");
		}
		else if(n==24){
			btn24.setLabel("");
		}
		else if(n==25){
			btn25.setLabel("");
		}
		else if(n==26){
			btn26.setLabel("");
		}
		else if(n==27){
			btn27.setLabel("");
		}
		else if(n==28){
			btn28.setLabel("");
		}
		else if(n==29){
			btn29.setLabel("");
		}
		else if(n==30){
			btn30.setLabel("");
		}
		else if(n==31){
			btn31.setLabel("");
		}
		else if(n==32){
			btn32.setLabel("");
		}
		else if(n==33){
			btn33.setLabel("");
		}
		else if(n==34){
			btn34.setLabel("");
		}
		else if(n==35){
			btn35.setLabel("");
		}
		else if(n==36){
			btn36.setLabel("");
		}
		else if(n==37){
			btn37.setLabel("");
		}
		else if(n==38){
			btn38.setLabel("");
		}
		else if(n==39){
			btn39.setLabel("");
		}
		else if(n==40){
			btn40.setLabel("");
		}
		else if(n==41){
			btn41.setLabel("");
		}
		else if(n==42){
			btn42.setLabel("");
		}
		else if(n==43){
			btn43.setLabel("");
		}
		else if(n==44){
			btn44.setLabel("");
		}
		else if(n==45){
			btn45.setLabel("");
		}
		else if(n==46){
			btn46.setLabel("");
		}
		else if(n==47){
			btn47.setLabel("");
		}
		else if(n==48){
			btn48.setLabel("");
		}
		else if(n==49){
			btn49.setLabel("");
		}
		else if(n==50){
			btn50.setLabel("");
		}
		else if(n==51){
			btn51.setLabel("");
		}
		else if(n==52){
			btn52.setLabel("");
		}
		else if(n==53){
			btn53.setLabel("");
		}
		else if(n==54){
			btn54.setLabel("");
		}
		else if(n==55){
			btn55.setLabel("");
		}
		else if(n==56){
			btn56.setLabel("");
		}
		else if(n==57){
			btn57.setLabel("");
		}
		else if(n==58){
			btn58.setLabel("");
		}
		else if(n==59){
			btn59.setLabel("");
		}
		else if(n==60){
			btn60.setLabel("");
		}
		else if(n==61){
			btn61.setLabel("");
		}
		else if(n==62){
			btn62.setLabel("");
		}
		else if(n==63){
			btn63.setLabel("");
		}
		else if(n==64){
			btn64.setLabel("");
		}
		else if(n==65){
			btn65.setLabel("");
		}
		else if(n==66){
			btn66.setLabel("");
		}
		else if(n==67){
			btn67.setLabel("");
		}
		else if(n==68){
			btn68.setLabel("");
		}
		else if(n==69){
			btn69.setLabel("");
		}
		else if(n==70){
			btn70.setLabel("");
		}
		else if(n==71){
			btn71.setLabel("");
		}
		else if(n==72){
			btn72.setLabel("");
		}
		else if(n==73){
			btn73.setLabel("");
		}
		else if(n==74){
			btn74.setLabel("");
		}
		else if(n==75){
			btn75.setLabel("");
		}
		else if(n==76){
			btn76.setLabel("");
		}
		else if(n==77){
			btn77.setLabel("");
		}
		else if(n==78){
			btn78.setLabel("");
		}
		else if(n==79){
			btn79.setLabel("");
		}
		else if(n==80){
			btn80.setLabel("");
		}
		else if(n==81){
			btn81.setLabel("");
		}
	}

	public void setText_lab(int n,int m){
		if(n==1){
			lab1.setText(Integer.toString(m));
		}
		else if(n==2){
			lab2.setText(Integer.toString(m));
		}
		else if(n==3){
			lab3.setText(Integer.toString(m));
		}
		else if(n==4){
			lab4.setText(Integer.toString(m));
		}
		else if(n==5){
			lab5.setText(Integer.toString(m));
		}
		else if(n==6){
			lab6.setText(Integer.toString(m));
		}
		else if(n==7){
			lab7.setText(Integer.toString(m));
		}
		else if(n==8){
			lab8.setText(Integer.toString(m));
		}
		else if(n==9){
			lab9.setText(Integer.toString(m));
		}
		else if(n==10){
			lab10.setText(Integer.toString(m));
		}
		else if(n==11){
			lab11.setText(Integer.toString(m));
		}
		else if(n==12){
			lab12.setText(Integer.toString(m));
		}
		else if(n==13){
			lab13.setText(Integer.toString(m));
		}
		else if(n==14){
			lab14.setText(Integer.toString(m));
		}
		else if(n==15){
			lab15.setText(Integer.toString(m));
		}
		else if(n==16){
			lab16.setText(Integer.toString(m));
		}
		else if(n==17){
			lab17.setText(Integer.toString(m));
		}
		else if(n==18){
			lab18.setText(Integer.toString(m));
		}
		else if(n==19){
			lab19.setText(Integer.toString(m));
		}
		else if(n==20){
			lab20.setText(Integer.toString(m));
		}
		else if(n==21){
			lab21.setText(Integer.toString(m));
		}
		else if(n==22){
			lab22.setText(Integer.toString(m));
		}
		else if(n==23){
			lab23.setText(Integer.toString(m));
		}
		else if(n==24){
			lab24.setText(Integer.toString(m));
		}
		else if(n==25){
			lab25.setText(Integer.toString(m));
		}
		else if(n==26){
			lab26.setText(Integer.toString(m));
		}
		else if(n==27){
			lab27.setText(Integer.toString(m));
		}
		else if(n==28){
			lab28.setText(Integer.toString(m));
		}
		else if(n==29){
			lab29.setText(Integer.toString(m));
		}
		else if(n==30){
			lab30.setText(Integer.toString(m));
		}
		else if(n==31){
			lab31.setText(Integer.toString(m));
		}
		else if(n==32){
			lab32.setText(Integer.toString(m));
		}
		else if(n==33){
			lab33.setText(Integer.toString(m));
		}
		else if(n==34){
			lab34.setText(Integer.toString(m));
		}
		else if(n==35){
			lab35.setText(Integer.toString(m));
		}
		else if(n==36){
			lab36.setText(Integer.toString(m));
		}
		else if(n==37){
			lab37.setText(Integer.toString(m));
		}
		else if(n==38){
			lab38.setText(Integer.toString(m));
		}
		else if(n==39){
			lab39.setText(Integer.toString(m));
		}
		else if(n==40){
			lab40.setText(Integer.toString(m));
		}
		else if(n==41){
			lab41.setText(Integer.toString(m));
		}
		else if(n==42){
			lab42.setText(Integer.toString(m));
		}
		else if(n==43){
			lab43.setText(Integer.toString(m));
		}
		else if(n==44){
			lab44.setText(Integer.toString(m));
		}
		else if(n==45){
			lab45.setText(Integer.toString(m));
		}
		else if(n==46){
			lab46.setText(Integer.toString(m));
		}
		else if(n==47){
			lab47.setText(Integer.toString(m));
		}
		else if(n==48){
			lab48.setText(Integer.toString(m));
		}
		else if(n==49){
			lab49.setText(Integer.toString(m));
		}
		else if(n==50){
			lab50.setText(Integer.toString(m));
		}
		else if(n==51){
			lab51.setText(Integer.toString(m));
		}
		else if(n==52){
			lab52.setText(Integer.toString(m));
		}
		else if(n==53){
			lab53.setText(Integer.toString(m));
		}
		else if(n==54){
			lab54.setText(Integer.toString(m));
		}
		else if(n==55){
			lab55.setText(Integer.toString(m));
		}
		else if(n==56){
			lab56.setText(Integer.toString(m));
		}
		else if(n==57){
			lab57.setText(Integer.toString(m));
		}
		else if(n==58){
			lab58.setText(Integer.toString(m));
		}
		else if(n==59){
			lab59.setText(Integer.toString(m));
		}
		else if(n==60){
			lab60.setText(Integer.toString(m));
		}
		else if(n==61){
			lab61.setText(Integer.toString(m));
		}
		else if(n==62){
			lab62.setText(Integer.toString(m));
		}
		else if(n==63){
			lab63.setText(Integer.toString(m));
		}
		else if(n==64){
			lab64.setText(Integer.toString(m));
		}
		else if(n==65){
			lab65.setText(Integer.toString(m));
		}
		else if(n==66){
			lab66.setText(Integer.toString(m));
		}
		else if(n==67){
			lab67.setText(Integer.toString(m));
		}
		else if(n==68){
			lab68.setText(Integer.toString(m));
		}
		else if(n==69){
			lab69.setText(Integer.toString(m));
		}
		else if(n==70){
			lab70.setText(Integer.toString(m));
		}
		else if(n==71){
			lab71.setText(Integer.toString(m));
		}
		else if(n==72){
			lab72.setText(Integer.toString(m));
		}
		else if(n==73){
			lab73.setText(Integer.toString(m));
		}
		else if(n==74){
			lab74.setText(Integer.toString(m));
		}
		else if(n==75){
			lab75.setText(Integer.toString(m));
		}
		else if(n==76){
			lab76.setText(Integer.toString(m));
		}
		else if(n==77){
			lab77.setText(Integer.toString(m));
		}
		else if(n==78){
			lab78.setText(Integer.toString(m));
		}
		else if(n==79){
			lab79.setText(Integer.toString(m));
		}
		else if(n==80){
			lab80.setText(Integer.toString(m));
		}
		else if(n==81){
			lab81.setText(Integer.toString(m));
		}
	}

	public void template(){//套入遊戲的難度樣板
		easy1[1]=2;
		easy1[2]=4;
		easy1[3]=5;
		easy1[4]=9;
		easy1[5]=11;
		easy1[6]=13;
		easy1[7]=15;
		easy1[8]=16;
		easy1[9]=17;
		easy1[10]=23;
		easy1[11]=24;
		easy1[12]=25;
		easy1[13]=30;
		easy1[14]=34;
		easy1[15]=35;
		easy1[16]=40;
		easy1[17]=42;
		easy1[18]=47;
		easy1[19]=48;
		easy1[20]=52;
		easy1[21]=57;
		easy1[22]=58;
		easy1[23]=59;
		easy1[24]=65;
		easy1[25]=66;
		easy1[26]=67;
		easy1[27]=69;
		easy1[28]=71;
		easy1[29]=73;
		easy1[30]=77;
		easy1[31]=78;
		easy1[32]=80;

		easy2[1]=2;
		easy2[2]=4;
		easy2[3]=5;
		easy2[4]=7;
		easy2[5]=9;
		easy2[6]=13;
		easy2[7]=16;
		easy2[8]=18;
		easy2[9]=21;
		easy2[10]=22;
		easy2[11]=26;
		easy2[12]=29;
		easy2[13]=32;
		easy2[14]=33;
		easy2[15]=35;
		easy2[16]=36;
		easy2[17]=46;
		easy2[18]=47;
		easy2[19]=49;
		easy2[20]=50;
		easy2[21]=53;
		easy2[22]=56;
		easy2[23]=60;
		easy2[24]=61;
		easy2[25]=64;
		easy2[26]=66;
		easy2[27]=69;
		easy2[28]=73;
		easy2[29]=75;
		easy2[30]=77;
		easy2[31]=78;
		easy2[32]=80;

		easy3[1]=2;
		easy3[2]=3;
		easy3[3]=4;
		easy3[4]=7;
		easy3[5]=10;
		easy3[6]=15;
		easy3[7]=17;
		easy3[8]=18;
		easy3[9]=19;
		easy3[10]=25;
		easy3[11]=27;
		easy3[12]=29;
		easy3[13]=32;
		easy3[14]=33;
		easy3[15]=36;
		easy3[16]=39;
		easy3[17]=43;
		easy3[18]=46;
		easy3[19]=49;
		easy3[20]=50;
		easy3[21]=53;
		easy3[22]=55;
		easy3[23]=57;
		easy3[24]=63;
		easy3[25]=64;
		easy3[26]=65;
		easy3[27]=67;
		easy3[28]=72;
		easy3[29]=75;
		easy3[30]=78;
		easy3[31]=79;
		easy3[32]=80;


		mid1[1]=2;
		mid1[2]=5;
		mid1[3]=9;
		mid1[4]=14;
		mid1[5]=16;
		mid1[6]=17;
		mid1[7]=21;
		mid1[8]=22;
		mid1[9]=23;
		mid1[10]=24;
		mid1[11]=25;
		mid1[12]=28;
		mid1[13]=29;
		mid1[14]=36;
		mid1[15]=37;
		mid1[16]=41;
		mid1[17]=45;
		mid1[18]=46;
		mid1[19]=49;
		mid1[20]=51;
		mid1[21]=55;
		mid1[22]=56;
		mid1[23]=60;
		mid1[24]=62;
		mid1[25]=71;
		mid1[26]=73;
		mid1[27]=75;
		mid1[28]=76;
		mid1[29]=78;
		mid1[30]=80;

		mid2[1]=2;
		mid2[2]=7;
		mid2[3]=12;
		mid2[4]=18;
		mid2[5]=19;
		mid2[6]=21;
		mid2[7]=23;
		mid2[8]=24;
		mid2[9]=29;
		mid2[10]=33;
		mid2[11]=35;
		mid2[12]=36;
		mid2[13]=38;
		mid2[14]=40;
		mid2[15]=41;
		mid2[16]=42;
		mid2[17]=46;
		mid2[18]=48;
		mid2[19]=49;
		mid2[20]=53;
		mid2[21]=58;
		mid2[22]=59;
		mid2[23]=60;
		mid2[24]=61;
		mid2[25]=63;
		mid2[26]=64;
		mid2[27]=70;
		mid2[28]=75;
		mid2[29]=80;

		mid3[1]=1;
		mid3[2]=2;
		mid3[3]=6;
		mid3[4]=10;
		mid3[5]=13;
		mid3[6]=14;
		mid3[7]=15;
		mid3[8]=20;
		mid3[9]=21;
		mid3[10]=26;
		mid3[11]=28;
		mid3[12]=32;
		mid3[13]=36;
		mid3[14]=37;
		mid3[15]=40;
		mid3[16]=42;
		mid3[17]=45;
		mid3[18]=46;
		mid3[19]=50;
		mid3[20]=54;
		mid3[21]=56;
		mid3[22]=61;
		mid3[23]=62;
		mid3[24]=67;
		mid3[25]=68;
		mid3[26]=69;
		mid3[27]=72;
		mid3[28]=77;
		mid3[29]=80;
		mid3[30]=81;


		hard1[1]=3;
		hard1[2]=7;
		hard1[3]=8;
		hard1[4]=9;
		hard1[5]=11;
		hard1[6]=20;
		hard1[7]=24;
		hard1[8]=26;
		hard1[9]=27;
		hard1[10]=29;
		hard1[11]=32;
		hard1[12]=40;
		hard1[13]=44;
		hard1[14]=46;
		hard1[15]=48;
		hard1[16]=54;
		hard1[17]=56;
		hard1[18]=58;
		hard1[19]=60;
		hard1[20]=68;
		hard1[21]=69;
		hard1[22]=72;
		hard1[23]=73;
		hard1[24]=76;

		hard2[1]=1;
		hard2[2]=4;
		hard2[3]=9;
		hard2[4]=15;
		hard2[5]=19;
		hard2[6]=21;
		hard2[7]=30;
		hard2[8]=34;
		hard2[9]=35;
		hard2[10]=36;
		hard2[11]=37;
		hard2[12]=43;
		hard2[13]=49;
		hard2[14]=52;
		hard2[15]=56;
		hard2[16]=57;
		hard2[17]=60;
		hard2[18]=65;
		hard2[19]=67;
		hard2[20]=68;
		hard2[21]=71;
		hard2[22]=77;
		hard2[23]=81;

		hard3[1]=2;
		hard3[2]=4;
		hard3[3]=7;
		hard3[4]=16;
		hard3[5]=17;
		hard3[6]=19;
		hard3[7]=22;
		hard3[8]=31;
		hard3[9]=37;
		hard3[10]=39;
		hard3[11]=42;
		hard3[12]=43;
		hard3[13]=45;
		hard3[14]=50;
		hard3[15]=51;
		hard3[16]=53;
		hard3[17]=55;
		hard3[18]=60;
		hard3[19]=61;
		hard3[20]=63;
		hard3[21]=66;
		hard3[22]=69;
		hard3[23]=71;
		hard3[24]=73;
		hard3[25]=74;
		hard3[26]=77;
		hard3[27]=81;
	}

	static void add_btn(int n){//依照n值加入btn
		if(n==1){
			frm.add(btn1);
		}
		else if(n==2){
			frm.add(btn2);
		}
		else if(n==3){
			frm.add(btn3);
		}
		else if(n==4){
			frm.add(btn4);
		}
		else if(n==5){
			frm.add(btn5);
		}
		else if(n==6){
			frm.add(btn6);
		}
		else if(n==7){
			frm.add(btn7);
		}
		else if(n==8){
			frm.add(btn8);
		}
		else if(n==9){
			frm.add(btn9);
		}
		else if(n==10){
			frm.add(btn10);
		}
		else if(n==11){
			frm.add(btn11);
		}
		else if(n==12){
			frm.add(btn12);
		}
		else if(n==13){
			frm.add(btn13);
		}
		else if(n==14){
			frm.add(btn14);
		}
		else if(n==15){
			frm.add(btn15);
		}
		else if(n==16){
			frm.add(btn16);
		}
		else if(n==17){
			frm.add(btn17);
		}
		else if(n==18){
			frm.add(btn18);
		}
		else if(n==19){
			frm.add(btn19);
		}
		else if(n==20){
			frm.add(btn20);
		}
		else if(n==21){
			frm.add(btn21);
		}
		else if(n==22){
			frm.add(btn22);
		}
		else if(n==23){
			frm.add(btn23);
		}
		else if(n==24){
			frm.add(btn24);
		}
		else if(n==25){
			frm.add(btn25);
		}
		else if(n==26){
			frm.add(btn26);
		}
		else if(n==27){
			frm.add(btn27);
		}
		else if(n==28){
			frm.add(btn28);
		}
		else if(n==29){
			frm.add(btn29);
		}
		else if(n==30){
			frm.add(btn30);
		}
		else if(n==31){
			frm.add(btn31);
		}
		else if(n==32){
			frm.add(btn32);
		}
		else if(n==33){
			frm.add(btn33);
		}
		else if(n==34){
			frm.add(btn34);
		}
		else if(n==35){
			frm.add(btn35);
		}
		else if(n==36){
			frm.add(btn36);
		}
		else if(n==37){
			frm.add(btn37);
		}
		else if(n==38){
			frm.add(btn38);
		}
		else if(n==39){
			frm.add(btn39);
		}
		else if(n==40){
			frm.add(btn40);
		}
		else if(n==41){
			frm.add(btn41);
		}
		else if(n==42){
			frm.add(btn42);
		}
		else if(n==43){
			frm.add(btn43);
		}
		else if(n==44){
			frm.add(btn44);
		}
		else if(n==45){
			frm.add(btn45);
		}
		else if(n==46){
			frm.add(btn46);
		}
		else if(n==47){
			frm.add(btn47);
		}
		else if(n==48){
			frm.add(btn48);
		}
		else if(n==49){
			frm.add(btn49);
		}
		else if(n==50){
			frm.add(btn50);
		}
		else if(n==51){
			frm.add(btn51);
		}
		else if(n==52){
			frm.add(btn52);
		}
		else if(n==53){
			frm.add(btn53);
		}
		else if(n==54){
			frm.add(btn54);
		}
		else if(n==55){
			frm.add(btn55);
		}
		else if(n==56){
			frm.add(btn56);
		}
		else if(n==57){
			frm.add(btn57);
		}
		else if(n==58){
			frm.add(btn58);
		}
		else if(n==59){
			frm.add(btn59);
		}
		else if(n==60){
			frm.add(btn60);
		}
		else if(n==61){
			frm.add(btn61);
		}
		else if(n==62){
			frm.add(btn62);
		}
		else if(n==63){
			frm.add(btn63);
		}
		else if(n==64){
			frm.add(btn64);
		}
		else if(n==65){
			frm.add(btn65);
		}
		else if(n==66){
			frm.add(btn66);
		}
		else if(n==67){
			frm.add(btn67);
		}
		else if(n==68){
			frm.add(btn68);
		}
		else if(n==69){
			frm.add(btn69);
		}
		else if(n==70){
			frm.add(btn70);
		}
		else if(n==71){
			frm.add(btn71);
		}
		else if(n==72){
			frm.add(btn72);
		}
		else if(n==73){
			frm.add(btn73);
		}
		else if(n==74){
			frm.add(btn74);
		}
		else if(n==75){
			frm.add(btn75);
		}
		else if(n==76){
			frm.add(btn76);
		}
		else if(n==77){
			frm.add(btn77);
		}
		else if(n==78){
			frm.add(btn78);
		}
		else if(n==79){
			frm.add(btn79);
		}
		else if(n==80){
			frm.add(btn80);
		}
		else if(n==81){
			frm.add(btn81);
		}
	}

	static void add_lab(int n){//依照n值加入lab
		if(n==1){
			frm.add(lab1);
		}
		else if(n==2){
			frm.add(lab2);
		}
		else if(n==3){
			frm.add(lab3);
		}
		else if(n==4){
			frm.add(lab4);
		}
		else if(n==5){
			frm.add(lab5);
		}
		else if(n==6){
			frm.add(lab6);
		}
		else if(n==7){
			frm.add(lab7);
		}
		else if(n==8){
			frm.add(lab8);
		}
		else if(n==9){
			frm.add(lab9);
		}
		else if(n==10){
			frm.add(lab10);
		}
		else if(n==11){
			frm.add(lab11);
		}
		else if(n==12){
			frm.add(lab12);
		}
		else if(n==13){
			frm.add(lab13);
		}
		else if(n==14){
			frm.add(lab14);
		}
		else if(n==15){
			frm.add(lab15);
		}
		else if(n==16){
			frm.add(lab16);
		}
		else if(n==17){
			frm.add(lab17);
		}
		else if(n==18){
			frm.add(lab18);
		}
		else if(n==19){
			frm.add(lab19);
		}
		else if(n==20){
			frm.add(lab20);
		}
		else if(n==21){
			frm.add(lab21);
		}
		else if(n==22){
			frm.add(lab22);
		}
		else if(n==23){
			frm.add(lab23);
		}
		else if(n==24){
			frm.add(lab24);
		}
		else if(n==25){
			frm.add(lab25);
		}
		else if(n==26){
			frm.add(lab26);
		}
		else if(n==27){
			frm.add(lab27);
		}
		else if(n==28){
			frm.add(lab28);
		}
		else if(n==29){
			frm.add(lab29);
		}
		else if(n==30){
			frm.add(lab30);
		}
		else if(n==31){
			frm.add(lab31);
		}
		else if(n==32){
			frm.add(lab32);
		}
		else if(n==33){
			frm.add(lab33);
		}
		else if(n==34){
			frm.add(lab34);
		}
		else if(n==35){
			frm.add(lab35);
		}
		else if(n==36){
			frm.add(lab36);
		}
		else if(n==37){
			frm.add(lab37);
		}
		else if(n==38){
			frm.add(lab38);
		}
		else if(n==39){
			frm.add(lab39);
		}
		else if(n==40){
			frm.add(lab40);
		}
		else if(n==41){
			frm.add(lab41);
		}
		else if(n==42){
			frm.add(lab42);
		}
		else if(n==43){
			frm.add(lab43);
		}
		else if(n==44){
			frm.add(lab44);
		}
		else if(n==45){
			frm.add(lab45);
		}
		else if(n==46){
			frm.add(lab46);
		}
		else if(n==47){
			frm.add(lab47);
		}
		else if(n==48){
			frm.add(lab48);
		}
		else if(n==49){
			frm.add(lab49);
		}
		else if(n==50){
			frm.add(lab50);
		}
		else if(n==51){
			frm.add(lab51);
		}
		else if(n==52){
			frm.add(lab52);
		}
		else if(n==53){
			frm.add(lab53);
		}
		else if(n==54){
			frm.add(lab54);
		}
		else if(n==55){
			frm.add(lab55);
		}
		else if(n==56){
			frm.add(lab56);
		}
		else if(n==57){
			frm.add(lab57);
		}
		else if(n==58){
			frm.add(lab58);
		}
		else if(n==59){
			frm.add(lab59);
		}
		else if(n==60){
			frm.add(lab60);
		}
		else if(n==61){
			frm.add(lab61);
		}
		else if(n==62){
			frm.add(lab62);
		}
		else if(n==63){
			frm.add(lab63);
		}
		else if(n==64){
			frm.add(lab64);
		}
		else if(n==65){
			frm.add(lab65);
		}
		else if(n==66){
			frm.add(lab66);
		}
		else if(n==67){
			frm.add(lab67);
		}
		else if(n==68){
			frm.add(lab68);
		}
		else if(n==69){
			frm.add(lab69);
		}
		else if(n==70){
			frm.add(lab70);
		}
		else if(n==71){
			frm.add(lab71);
		}
		else if(n==72){
			frm.add(lab72);
		}
		else if(n==73){
			frm.add(lab73);
		}
		else if(n==74){
			frm.add(lab74);
		}
		else if(n==75){
			frm.add(lab75);
		}
		else if(n==76){
			frm.add(lab76);
		}
		else if(n==77){
			frm.add(lab77);
		}
		else if(n==78){
			frm.add(lab78);
		}
		else if(n==79){
			frm.add(lab79);
		}
		else if(n==80){
			frm.add(lab80);
		}
		else if(n==81){
			frm.add(lab81);
		}
	}

	static void remove_btn(int n){//依照n值加入btn
		if(n==1){
			frm.remove(btn1);
		}
		else if(n==2){
			frm.remove(btn2);
		}
		else if(n==3){
			frm.remove(btn3);
		}
		else if(n==4){
			frm.remove(btn4);
		}
		else if(n==5){
			frm.remove(btn5);
		}
		else if(n==6){
			frm.remove(btn6);
		}
		else if(n==7){
			frm.remove(btn7);
		}
		else if(n==8){
			frm.remove(btn8);
		}
		else if(n==9){
			frm.remove(btn9);
		}
		else if(n==10){
			frm.remove(btn10);
		}
		else if(n==11){
			frm.remove(btn11);
		}
		else if(n==12){
			frm.remove(btn12);
		}
		else if(n==13){
			frm.remove(btn13);
		}
		else if(n==14){
			frm.remove(btn14);
		}
		else if(n==15){
			frm.remove(btn15);
		}
		else if(n==16){
			frm.remove(btn16);
		}
		else if(n==17){
			frm.remove(btn17);
		}
		else if(n==18){
			frm.remove(btn18);
		}
		else if(n==19){
			frm.remove(btn19);
		}
		else if(n==20){
			frm.remove(btn20);
		}
		else if(n==21){
			frm.remove(btn21);
		}
		else if(n==22){
			frm.remove(btn22);
		}
		else if(n==23){
			frm.remove(btn23);
		}
		else if(n==24){
			frm.remove(btn24);
		}
		else if(n==25){
			frm.remove(btn25);
		}
		else if(n==26){
			frm.remove(btn26);
		}
		else if(n==27){
			frm.remove(btn27);
		}
		else if(n==28){
			frm.remove(btn28);
		}
		else if(n==29){
			frm.remove(btn29);
		}
		else if(n==30){
			frm.remove(btn30);
		}
		else if(n==31){
			frm.remove(btn31);
		}
		else if(n==32){
			frm.remove(btn32);
		}
		else if(n==33){
			frm.remove(btn33);
		}
		else if(n==34){
			frm.remove(btn34);
		}
		else if(n==35){
			frm.remove(btn35);
		}
		else if(n==36){
			frm.remove(btn36);
		}
		else if(n==37){
			frm.remove(btn37);
		}
		else if(n==38){
			frm.remove(btn38);
		}
		else if(n==39){
			frm.remove(btn39);
		}
		else if(n==40){
			frm.remove(btn40);
		}
		else if(n==41){
			frm.remove(btn41);
		}
		else if(n==42){
			frm.remove(btn42);
		}
		else if(n==43){
			frm.remove(btn43);
		}
		else if(n==44){
			frm.remove(btn44);
		}
		else if(n==45){
			frm.remove(btn45);
		}
		else if(n==46){
			frm.remove(btn46);
		}
		else if(n==47){
			frm.remove(btn47);
		}
		else if(n==48){
			frm.remove(btn48);
		}
		else if(n==49){
			frm.remove(btn49);
		}
		else if(n==50){
			frm.remove(btn50);
		}
		else if(n==51){
			frm.remove(btn51);
		}
		else if(n==52){
			frm.remove(btn52);
		}
		else if(n==53){
			frm.remove(btn53);
		}
		else if(n==54){
			frm.remove(btn54);
		}
		else if(n==55){
			frm.remove(btn55);
		}
		else if(n==56){
			frm.remove(btn56);
		}
		else if(n==57){
			frm.remove(btn57);
		}
		else if(n==58){
			frm.remove(btn58);
		}
		else if(n==59){
			frm.remove(btn59);
		}
		else if(n==60){
			frm.remove(btn60);
		}
		else if(n==61){
			frm.remove(btn61);
		}
		else if(n==62){
			frm.remove(btn62);
		}
		else if(n==63){
			frm.remove(btn63);
		}
		else if(n==64){
			frm.remove(btn64);
		}
		else if(n==65){
			frm.remove(btn65);
		}
		else if(n==66){
			frm.remove(btn66);
		}
		else if(n==67){
			frm.remove(btn67);
		}
		else if(n==68){
			frm.remove(btn68);
		}
		else if(n==69){
			frm.remove(btn69);
		}
		else if(n==70){
			frm.remove(btn70);
		}
		else if(n==71){
			frm.remove(btn71);
		}
		else if(n==72){
			frm.remove(btn72);
		}
		else if(n==73){
			frm.remove(btn73);
		}
		else if(n==74){
			frm.remove(btn74);
		}
		else if(n==75){
			frm.remove(btn75);
		}
		else if(n==76){
			frm.remove(btn76);
		}
		else if(n==77){
			frm.remove(btn77);
		}
		else if(n==78){
			frm.remove(btn78);
		}
		else if(n==79){
			frm.remove(btn79);
		}
		else if(n==80){
			frm.remove(btn80);
		}
		else if(n==81){
			frm.remove(btn81);
		}
	}

	static void remove_lab(int n){//依照n值加入lab
		if(n==1){
			frm.remove(lab1);
		}
		else if(n==2){
			frm.remove(lab2);
		}
		else if(n==3){
			frm.remove(lab3);
		}
		else if(n==4){
			frm.remove(lab4);
		}
		else if(n==5){
			frm.remove(lab5);
		}
		else if(n==6){
			frm.remove(lab6);
		}
		else if(n==7){
			frm.remove(lab7);
		}
		else if(n==8){
			frm.remove(lab8);
		}
		else if(n==9){
			frm.remove(lab9);
		}
		else if(n==10){
			frm.remove(lab10);
		}
		else if(n==11){
			frm.remove(lab11);
		}
		else if(n==12){
			frm.remove(lab12);
		}
		else if(n==13){
			frm.remove(lab13);
		}
		else if(n==14){
			frm.remove(lab14);
		}
		else if(n==15){
			frm.remove(lab15);
		}
		else if(n==16){
			frm.remove(lab16);
		}
		else if(n==17){
			frm.remove(lab17);
		}
		else if(n==18){
			frm.remove(lab18);
		}
		else if(n==19){
			frm.remove(lab19);
		}
		else if(n==20){
			frm.remove(lab20);
		}
		else if(n==21){
			frm.remove(lab21);
		}
		else if(n==22){
			frm.remove(lab22);
		}
		else if(n==23){
			frm.remove(lab23);
		}
		else if(n==24){
			frm.remove(lab24);
		}
		else if(n==25){
			frm.remove(lab25);
		}
		else if(n==26){
			frm.remove(lab26);
		}
		else if(n==27){
			frm.remove(lab27);
		}
		else if(n==28){
			frm.remove(lab28);
		}
		else if(n==29){
			frm.remove(lab29);
		}
		else if(n==30){
			frm.remove(lab30);
		}
		else if(n==31){
			frm.remove(lab31);
		}
		else if(n==32){
			frm.remove(lab32);
		}
		else if(n==33){
			frm.remove(lab33);
		}
		else if(n==34){
			frm.remove(lab34);
		}
		else if(n==35){
			frm.remove(lab35);
		}
		else if(n==36){
			frm.remove(lab36);
		}
		else if(n==37){
			frm.remove(lab37);
		}
		else if(n==38){
			frm.remove(lab38);
		}
		else if(n==39){
			frm.remove(lab39);
		}
		else if(n==40){
			frm.remove(lab40);
		}
		else if(n==41){
			frm.remove(lab41);
		}
		else if(n==42){
			frm.remove(lab42);
		}
		else if(n==43){
			frm.remove(lab43);
		}
		else if(n==44){
			frm.remove(lab44);
		}
		else if(n==45){
			frm.remove(lab45);
		}
		else if(n==46){
			frm.remove(lab46);
		}
		else if(n==47){
			frm.remove(lab47);
		}
		else if(n==48){
			frm.remove(lab48);
		}
		else if(n==49){
			frm.remove(lab49);
		}
		else if(n==50){
			frm.remove(lab50);
		}
		else if(n==51){
			frm.remove(lab51);
		}
		else if(n==52){
			frm.remove(lab52);
		}
		else if(n==53){
			frm.remove(lab53);
		}
		else if(n==54){
			frm.remove(lab54);
		}
		else if(n==55){
			frm.remove(lab55);
		}
		else if(n==56){
			frm.remove(lab56);
		}
		else if(n==57){
			frm.remove(lab57);
		}
		else if(n==58){
			frm.remove(lab58);
		}
		else if(n==59){
			frm.remove(lab59);
		}
		else if(n==60){
			frm.remove(lab60);
		}
		else if(n==61){
			frm.remove(lab61);
		}
		else if(n==62){
			frm.remove(lab62);
		}
		else if(n==63){
			frm.remove(lab63);
		}
		else if(n==64){
			frm.remove(lab64);
		}
		else if(n==65){
			frm.remove(lab65);
		}
		else if(n==66){
			frm.remove(lab66);
		}
		else if(n==67){
			frm.remove(lab67);
		}
		else if(n==68){
			frm.remove(lab68);
		}
		else if(n==69){
			frm.remove(lab69);
		}
		else if(n==70){
			frm.remove(lab70);
		}
		else if(n==71){
			frm.remove(lab71);
		}
		else if(n==72){
			frm.remove(lab72);
		}
		else if(n==73){
			frm.remove(lab73);
		}
		else if(n==74){
			frm.remove(lab74);
		}
		else if(n==75){
			frm.remove(lab75);
		}
		else if(n==76){
			frm.remove(lab76);
		}
		else if(n==77){
			frm.remove(lab77);
		}
		else if(n==78){
			frm.remove(lab78);
		}
		else if(n==79){
			frm.remove(lab79);
		}
		else if(n==80){
			frm.remove(lab80);
		}
		else if(n==81){
			frm.remove(lab81);
		}
	}

	static void setWord_label(){//設定label字體大小
		lab1.setFont(fnt1);
		lab2.setFont(fnt1);
		lab3.setFont(fnt1);
		lab4.setFont(fnt1);
		lab5.setFont(fnt1);
		lab6.setFont(fnt1);
		lab7.setFont(fnt1);
		lab8.setFont(fnt1);
		lab9.setFont(fnt1);
		lab10.setFont(fnt1);
		lab11.setFont(fnt1);
		lab12.setFont(fnt1);
		lab13.setFont(fnt1);
		lab14.setFont(fnt1);
		lab15.setFont(fnt1);
		lab16.setFont(fnt1);
		lab17.setFont(fnt1);
		lab18.setFont(fnt1);
		lab19.setFont(fnt1);
		lab20.setFont(fnt1);
		lab21.setFont(fnt1);
		lab22.setFont(fnt1);
		lab23.setFont(fnt1);
		lab24.setFont(fnt1);
		lab25.setFont(fnt1);
		lab26.setFont(fnt1);
		lab27.setFont(fnt1);
		lab28.setFont(fnt1);
		lab29.setFont(fnt1);
		lab30.setFont(fnt1);
		lab31.setFont(fnt1);
		lab32.setFont(fnt1);
		lab33.setFont(fnt1);
		lab34.setFont(fnt1);
		lab35.setFont(fnt1);
		lab36.setFont(fnt1);
		lab37.setFont(fnt1);
		lab38.setFont(fnt1);
		lab39.setFont(fnt1);
		lab40.setFont(fnt1);
		lab41.setFont(fnt1);
		lab42.setFont(fnt1);
		lab43.setFont(fnt1);
		lab44.setFont(fnt1);
		lab45.setFont(fnt1);
		lab46.setFont(fnt1);
		lab47.setFont(fnt1);
		lab48.setFont(fnt1);
		lab49.setFont(fnt1);
		lab50.setFont(fnt1);
		lab51.setFont(fnt1);
		lab52.setFont(fnt1);
		lab53.setFont(fnt1);
		lab54.setFont(fnt1);
		lab55.setFont(fnt1);
		lab56.setFont(fnt1);
		lab57.setFont(fnt1);
		lab58.setFont(fnt1);
		lab59.setFont(fnt1);
		lab60.setFont(fnt1);
		lab61.setFont(fnt1);
		lab62.setFont(fnt1);
		lab63.setFont(fnt1);
		lab64.setFont(fnt1);
		lab65.setFont(fnt1);
		lab66.setFont(fnt1);
		lab67.setFont(fnt1);
		lab68.setFont(fnt1);
		lab69.setFont(fnt1);
		lab70.setFont(fnt1);
		lab71.setFont(fnt1);
		lab72.setFont(fnt1);
		lab73.setFont(fnt1);
		lab74.setFont(fnt1);
		lab75.setFont(fnt1);
		lab76.setFont(fnt1);
		lab77.setFont(fnt1);
		lab78.setFont(fnt1);
		lab79.setFont(fnt1);
		lab80.setFont(fnt1);
		lab81.setFont(fnt1);
	}

	static void setWord_button(){//設定button字體大小
		btn1.setFont(fnt1);
		btn2.setFont(fnt1);
		btn3.setFont(fnt1);
		btn4.setFont(fnt1);
		btn5.setFont(fnt1);
		btn6.setFont(fnt1);
		btn7.setFont(fnt1);
		btn8.setFont(fnt1);
		btn9.setFont(fnt1);
		btn10.setFont(fnt1);
		btn11.setFont(fnt1);
		btn12.setFont(fnt1);
		btn13.setFont(fnt1);
		btn14.setFont(fnt1);
		btn15.setFont(fnt1);
		btn16.setFont(fnt1);
		btn17.setFont(fnt1);
		btn18.setFont(fnt1);
		btn19.setFont(fnt1);
		btn20.setFont(fnt1);
		btn21.setFont(fnt1);
		btn22.setFont(fnt1);
		btn23.setFont(fnt1);
		btn24.setFont(fnt1);
		btn25.setFont(fnt1);
		btn26.setFont(fnt1);
		btn27.setFont(fnt1);
		btn28.setFont(fnt1);
		btn29.setFont(fnt1);
		btn30.setFont(fnt1);
		btn31.setFont(fnt1);
		btn32.setFont(fnt1);
		btn33.setFont(fnt1);
		btn34.setFont(fnt1);
		btn35.setFont(fnt1);
		btn36.setFont(fnt1);
		btn37.setFont(fnt1);
		btn38.setFont(fnt1);
		btn39.setFont(fnt1);
		btn40.setFont(fnt1);
		btn41.setFont(fnt1);
		btn42.setFont(fnt1);
		btn43.setFont(fnt1);
		btn44.setFont(fnt1);
		btn45.setFont(fnt1);
		btn46.setFont(fnt1);
		btn47.setFont(fnt1);
		btn48.setFont(fnt1);
		btn49.setFont(fnt1);
		btn50.setFont(fnt1);
		btn51.setFont(fnt1);
		btn52.setFont(fnt1);
		btn53.setFont(fnt1);
		btn54.setFont(fnt1);
		btn55.setFont(fnt1);
		btn56.setFont(fnt1);
		btn57.setFont(fnt1);
		btn58.setFont(fnt1);
		btn59.setFont(fnt1);
		btn60.setFont(fnt1);
		btn61.setFont(fnt1);
		btn62.setFont(fnt1);
		btn63.setFont(fnt1);
		btn64.setFont(fnt1);
		btn65.setFont(fnt1);
		btn66.setFont(fnt1);
		btn67.setFont(fnt1);
		btn68.setFont(fnt1);
		btn69.setFont(fnt1);
		btn70.setFont(fnt1);
		btn71.setFont(fnt1);
		btn72.setFont(fnt1);
		btn73.setFont(fnt1);
		btn74.setFont(fnt1);
		btn75.setFont(fnt1);
		btn76.setFont(fnt1);
		btn77.setFont(fnt1);
		btn78.setFont(fnt1);
		btn79.setFont(fnt1);
		btn80.setFont(fnt1);
		btn81.setFont(fnt1);
	}

	static void setWord_choose_num()
	{
		choose_num1.setFont(fnt);
		choose_num2.setFont(fnt);
		choose_num3.setFont(fnt);
		choose_num4.setFont(fnt);
		choose_num5.setFont(fnt);
		choose_num6.setFont(fnt);
		choose_num7.setFont(fnt);
		choose_num8.setFont(fnt);
		choose_num9.setFont(fnt);
	}

	static void setColor_label(){//設定label字體與背景顏色
		lab1.setBackground(Color.gray);
		lab1.setForeground(Color.blue);
		lab2.setBackground(Color.gray);
		lab2.setForeground(Color.blue);
		lab3.setBackground(Color.gray);
		lab3.setForeground(Color.blue);
		lab10.setBackground(Color.gray);
		lab10.setForeground(Color.blue);
		lab11.setBackground(Color.gray);
		lab11.setForeground(Color.blue);
		lab12.setBackground(Color.gray);
		lab12.setForeground(Color.blue);
		lab19.setBackground(Color.gray);
		lab19.setForeground(Color.blue);
		lab20.setBackground(Color.gray);
		lab20.setForeground(Color.blue);
		lab21.setBackground(Color.gray);
		lab21.setForeground(Color.blue);

		lab4.setBackground(Color.white);
		lab4.setForeground(Color.blue);
		lab5.setBackground(Color.white);
		lab5.setForeground(Color.blue);
		lab6.setBackground(Color.white);
		lab6.setForeground(Color.blue);
		lab13.setBackground(Color.white);
		lab13.setForeground(Color.blue);
		lab14.setBackground(Color.white);
		lab14.setForeground(Color.blue);
		lab15.setBackground(Color.white);
		lab15.setForeground(Color.blue);
		lab22.setBackground(Color.white);
		lab22.setForeground(Color.blue);
		lab23.setBackground(Color.white);
		lab23.setForeground(Color.blue);
		lab24.setBackground(Color.white);
		lab24.setForeground(Color.blue);

		lab7.setBackground(Color.gray);
		lab7.setForeground(Color.blue);
		lab8.setBackground(Color.gray);
		lab8.setForeground(Color.blue);
		lab9.setBackground(Color.gray);
		lab9.setForeground(Color.blue);
		lab16.setBackground(Color.gray);
		lab16.setForeground(Color.blue);
		lab17.setBackground(Color.gray);
		lab17.setForeground(Color.blue);
		lab18.setBackground(Color.gray);
		lab18.setForeground(Color.blue);
		lab25.setBackground(Color.gray);
		lab25.setForeground(Color.blue);
		lab26.setBackground(Color.gray);
		lab26.setForeground(Color.blue);
		lab27.setBackground(Color.gray);
		lab27.setForeground(Color.blue);

		lab28.setBackground(Color.white);
		lab28.setForeground(Color.blue);
		lab29.setBackground(Color.white);
		lab29.setForeground(Color.blue);
		lab30.setBackground(Color.white);
		lab30.setForeground(Color.blue);
		lab37.setBackground(Color.white);
		lab37.setForeground(Color.blue);
		lab38.setBackground(Color.white);
		lab38.setForeground(Color.blue);
		lab39.setBackground(Color.white);
		lab39.setForeground(Color.blue);
		lab46.setBackground(Color.white);
		lab46.setForeground(Color.blue);
		lab47.setBackground(Color.white);
		lab47.setForeground(Color.blue);
		lab48.setBackground(Color.white);
		lab48.setForeground(Color.blue);

		lab31.setBackground(Color.gray);
		lab31.setForeground(Color.blue);
		lab32.setBackground(Color.gray);
		lab32.setForeground(Color.blue);
		lab33.setBackground(Color.gray);
		lab33.setForeground(Color.blue);
		lab40.setBackground(Color.gray);
		lab40.setForeground(Color.blue);
		lab41.setBackground(Color.gray);
		lab41.setForeground(Color.blue);
		lab42.setBackground(Color.gray);
		lab42.setForeground(Color.blue);
		lab49.setBackground(Color.gray);
		lab49.setForeground(Color.blue);
		lab50.setBackground(Color.gray);
		lab50.setForeground(Color.blue);
		lab51.setBackground(Color.gray);
		lab51.setForeground(Color.blue);

		lab34.setBackground(Color.white);
		lab34.setForeground(Color.blue);
		lab35.setBackground(Color.white);
		lab35.setForeground(Color.blue);
		lab36.setBackground(Color.white);
		lab36.setForeground(Color.blue);
		lab43.setBackground(Color.white);
		lab43.setForeground(Color.blue);
		lab44.setBackground(Color.white);
		lab44.setForeground(Color.blue);
		lab45.setBackground(Color.white);
		lab45.setForeground(Color.blue);
		lab52.setBackground(Color.white);
		lab52.setForeground(Color.blue);
		lab53.setBackground(Color.white);
		lab53.setForeground(Color.blue);
		lab54.setBackground(Color.white);
		lab54.setForeground(Color.blue);

		lab55.setBackground(Color.gray);
		lab55.setForeground(Color.blue);
		lab56.setBackground(Color.gray);
		lab56.setForeground(Color.blue);
		lab57.setBackground(Color.gray);
		lab57.setForeground(Color.blue);
		lab64.setBackground(Color.gray);
		lab64.setForeground(Color.blue);
		lab65.setBackground(Color.gray);
		lab65.setForeground(Color.blue);
		lab66.setBackground(Color.gray);
		lab66.setForeground(Color.blue);
		lab73.setBackground(Color.gray);
		lab73.setForeground(Color.blue);
		lab74.setBackground(Color.gray);
		lab74.setForeground(Color.blue);
		lab75.setBackground(Color.gray);
		lab75.setForeground(Color.blue);

		lab58.setBackground(Color.white);
		lab58.setForeground(Color.blue);
		lab59.setBackground(Color.white);
		lab59.setForeground(Color.blue);
		lab60.setBackground(Color.white);
		lab60.setForeground(Color.blue);
		lab67.setBackground(Color.white);
		lab67.setForeground(Color.blue);
		lab68.setBackground(Color.white);
		lab68.setForeground(Color.blue);
		lab69.setBackground(Color.white);
		lab69.setForeground(Color.blue);
		lab76.setBackground(Color.white);
		lab76.setForeground(Color.blue);
		lab77.setBackground(Color.white);
		lab77.setForeground(Color.blue);
		lab78.setBackground(Color.white);
		lab78.setForeground(Color.blue);

		lab61.setBackground(Color.gray);
		lab61.setForeground(Color.blue);
		lab62.setBackground(Color.gray);
		lab62.setForeground(Color.blue);
		lab63.setBackground(Color.gray);
		lab63.setForeground(Color.blue);
		lab70.setBackground(Color.gray);
		lab70.setForeground(Color.blue);
		lab71.setBackground(Color.gray);
		lab71.setForeground(Color.blue);
		lab72.setBackground(Color.gray);
		lab72.setForeground(Color.blue);
		lab79.setBackground(Color.gray);
		lab79.setForeground(Color.blue);
		lab80.setBackground(Color.gray);
		lab80.setForeground(Color.blue);
		lab81.setBackground(Color.gray);
		lab81.setForeground(Color.blue);
	}

	static void setColor_button(){//設定button字體與背景顏色
		btn1.setBackground(Color.gray);
		btn1.setForeground(Color.white);
		btn2.setBackground(Color.gray);
		btn2.setForeground(Color.white);
		btn3.setBackground(Color.gray);
		btn3.setForeground(Color.white);
		btn10.setBackground(Color.gray);
		btn10.setForeground(Color.white);
		btn11.setBackground(Color.gray);
		btn11.setForeground(Color.white);
		btn12.setBackground(Color.gray);
		btn12.setForeground(Color.white);
		btn19.setBackground(Color.gray);
		btn19.setForeground(Color.white);
		btn20.setBackground(Color.gray);
		btn20.setForeground(Color.white);
		btn21.setBackground(Color.gray);
		btn21.setForeground(Color.white);

		btn4.setBackground(Color.white);
		btn4.setForeground(Color.black);
		btn5.setBackground(Color.white);
		btn5.setForeground(Color.black);
		btn6.setBackground(Color.white);
		btn6.setForeground(Color.black);
		btn13.setBackground(Color.white);
		btn13.setForeground(Color.black);
		btn14.setBackground(Color.white);
		btn14.setForeground(Color.black);
		btn15.setBackground(Color.white);
		btn15.setForeground(Color.black);
		btn22.setBackground(Color.white);
		btn22.setForeground(Color.black);
		btn23.setBackground(Color.white);
		btn23.setForeground(Color.black);
		btn24.setBackground(Color.white);
		btn24.setForeground(Color.black);

		btn7.setBackground(Color.gray);
		btn7.setForeground(Color.white);
		btn8.setBackground(Color.gray);
		btn8.setForeground(Color.white);
		btn9.setBackground(Color.gray);
		btn9.setForeground(Color.white);
		btn16.setBackground(Color.gray);
		btn16.setForeground(Color.white);
		btn17.setBackground(Color.gray);
		btn17.setForeground(Color.white);
		btn18.setBackground(Color.gray);
		btn18.setForeground(Color.white);
		btn25.setBackground(Color.gray);
		btn25.setForeground(Color.white);
		btn26.setBackground(Color.gray);
		btn26.setForeground(Color.white);
		btn27.setBackground(Color.gray);
		btn27.setForeground(Color.white);

		btn28.setBackground(Color.white);
		btn28.setForeground(Color.black);
		btn29.setBackground(Color.white);
		btn29.setForeground(Color.black);
		btn30.setBackground(Color.white);
		btn30.setForeground(Color.black);
		btn37.setBackground(Color.white);
		btn37.setForeground(Color.black);
		btn38.setBackground(Color.white);
		btn38.setForeground(Color.black);
		btn39.setBackground(Color.white);
		btn39.setForeground(Color.black);
		btn46.setBackground(Color.white);
		btn46.setForeground(Color.black);
		btn47.setBackground(Color.white);
		btn47.setForeground(Color.black);
		btn48.setBackground(Color.white);
		btn48.setForeground(Color.black);

		btn31.setBackground(Color.gray);
		btn31.setForeground(Color.white);
		btn32.setBackground(Color.gray);
		btn32.setForeground(Color.white);
		btn33.setBackground(Color.gray);
		btn33.setForeground(Color.white);
		btn40.setBackground(Color.gray);
		btn40.setForeground(Color.white);
		btn41.setBackground(Color.gray);
		btn41.setForeground(Color.white);
		btn42.setBackground(Color.gray);
		btn42.setForeground(Color.white);
		btn49.setBackground(Color.gray);
		btn49.setForeground(Color.white);
		btn50.setBackground(Color.gray);
		btn50.setForeground(Color.white);
		btn51.setBackground(Color.gray);
		btn51.setForeground(Color.white);

		btn34.setBackground(Color.white);
		btn34.setForeground(Color.black);
		btn35.setBackground(Color.white);
		btn35.setForeground(Color.black);
		btn36.setBackground(Color.white);
		btn36.setForeground(Color.black);
		btn43.setBackground(Color.white);
		btn43.setForeground(Color.black);
		btn44.setBackground(Color.white);
		btn44.setForeground(Color.black);
		btn45.setBackground(Color.white);
		btn45.setForeground(Color.black);
		btn52.setBackground(Color.white);
		btn52.setForeground(Color.black);
		btn53.setBackground(Color.white);
		btn53.setForeground(Color.black);
		btn54.setBackground(Color.white);
		btn54.setForeground(Color.black);

		btn55.setBackground(Color.gray);
		btn55.setForeground(Color.white);
		btn56.setBackground(Color.gray);
		btn56.setForeground(Color.white);
		btn57.setBackground(Color.gray);
		btn57.setForeground(Color.white);
		btn64.setBackground(Color.gray);
		btn64.setForeground(Color.white);
		btn65.setBackground(Color.gray);
		btn65.setForeground(Color.white);
		btn66.setBackground(Color.gray);
		btn66.setForeground(Color.white);
		btn73.setBackground(Color.gray);
		btn73.setForeground(Color.white);
		btn74.setBackground(Color.gray);
		btn74.setForeground(Color.white);
		btn75.setBackground(Color.gray);
		btn75.setForeground(Color.white);

		btn58.setBackground(Color.white);
		btn58.setForeground(Color.black);
		btn59.setBackground(Color.white);
		btn59.setForeground(Color.black);
		btn60.setBackground(Color.white);
		btn60.setForeground(Color.black);
		btn67.setBackground(Color.white);
		btn67.setForeground(Color.black);
		btn68.setBackground(Color.white);
		btn68.setForeground(Color.black);
		btn69.setBackground(Color.white);
		btn69.setForeground(Color.black);
		btn76.setBackground(Color.white);
		btn76.setForeground(Color.black);
		btn77.setBackground(Color.white);
		btn77.setForeground(Color.black);
		btn78.setBackground(Color.white);
		btn78.setForeground(Color.black);

		btn61.setBackground(Color.gray);
		btn61.setForeground(Color.white);
		btn62.setBackground(Color.gray);
		btn62.setForeground(Color.white);
		btn63.setBackground(Color.gray);
		btn63.setForeground(Color.white);
		btn70.setBackground(Color.gray);
		btn70.setForeground(Color.white);
		btn71.setBackground(Color.gray);
		btn71.setForeground(Color.white);
		btn72.setBackground(Color.gray);
		btn72.setForeground(Color.white);
		btn79.setBackground(Color.gray);
		btn79.setForeground(Color.white);
		btn80.setBackground(Color.gray);
		btn80.setForeground(Color.white);
		btn81.setBackground(Color.gray);
		btn81.setForeground(Color.white);
	}

	static void setColor_choose_num()
	{
		choose_num1.setBackground(Color.pink);
		choose_num1.setForeground(Color.black);
		choose_num2.setBackground(Color.pink);
		choose_num2.setForeground(Color.black);
		choose_num3.setBackground(Color.pink);
		choose_num3.setForeground(Color.black);
		choose_num4.setBackground(Color.pink);
		choose_num4.setForeground(Color.black);
		choose_num5.setBackground(Color.pink);
		choose_num5.setForeground(Color.black);
		choose_num6.setBackground(Color.pink);
		choose_num6.setForeground(Color.black);
		choose_num7.setBackground(Color.pink);
		choose_num7.setForeground(Color.black);
		choose_num8.setBackground(Color.pink);
		choose_num8.setForeground(Color.black);
		choose_num9.setBackground(Color.pink);
		choose_num9.setForeground(Color.black);
	}
}