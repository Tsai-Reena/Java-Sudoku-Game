/*public class math
{
	static boolean check_column(int a,int b,int num[][]){//�ˬd���
		boolean re=false;
		for(int i=a-1;i>0;i--){
			if(num[b][a]==num[b][i]){
				re=true;//������
				break;
			}
			else{
				re=false;
			}
		}
		return re;
	}

	static boolean check_row(int a,int b,int num[][]){//�ˬd����
		boolean re=false;
		for(int i=b-1;i>0;i--){
			if(num[b][a]==num[i][a]){
				re=true;//������
				break;
			}
			else{
				re=false;
			}
		}
		return re;
	}

	public static void main(String args[])
	{
		int num[][]=new int[10][10];
		for(int j=1;j<=9;j++){//j��ܲ�j����
			for(int i=1;i<=9;i++){//i��ܲ�i���
				if(i==1 && j==1){
					while(true){//���ͫD0���Ӧ��
						num[j][i]=(int)(10*Math.random());
						if(num[j][i]!=0){
							break;
						}
					}
				}
				else{
					if(j==1){
						while(check_column(j,i,num)==true || num[j][i]==0){
							while(true){//���ͫD0���Ӧ��
								num[j][i]=(int)(10*Math.random());
								if(num[j][i]!=0){
									break;
								}
							}
						}
					}
					else{
						while(check_column(j,i,num)==true || check_row(j,i,num)==true|| num[j][i]==0){
							while(true){//���ͫD0���Ӧ��
								num[j][i]=(int)(10*Math.random());
								if(num[j][i]!=0){
									break;
								}
							}
						}
					}
				}
			}
		}

		for(int i=1;i<=9;i++){
			for(int j=1;j<=9;j++){
				System.out.print(num[i][j]+" ");
				if(j%3==0){
					System.out.print("/ ");
				}
			}
			System.out.println();
			if(i%3==0){
				System.out.println();
			}
		}
	}
}*/

/*public class math
{
	static boolean check_row(int a,int b,int num[][])//�ˬd����
	{
		boolean re=false;
		for(int i=a-1;i>0;i--){
			if(num[a][b]==num[i][b]){
				re=true;
				break;
			}
		}
		return re;
	}

	static boolean check_column(int a,int b,int num[][])//�ˬd���
	{
		boolean re=false;
		for(int j=b-1;j>0;j--){
			if(num[a][b]==num[a][j]){
				re=true;
				break;
			}
		}
		return re;
	}

	public static void main(String args[])
	{
		int num[][]=new int[10][10];
		for(int j=1;j<=9;j++){
			for(int i=1;i<=9;i++){
				while(true){
					num[i][j]=(int)(10*Math.random());
					if(num[i][j]!=0){
						break;
					}
				}
				while(check_row(i,j,num)==true || check_column(i,j,num)==true){
					while(true){
						num[i][j]=(int)(10*Math.random());
						if(num[i][j]!=0){
							break;
						}
					}
				}
			}
		}

		for(int i=1;i<=9;i++){
			for(int j=1;j<=9;j++){
				System.out.print(num[i][j]+" ");
				if(j%3==0){
					System.out.print("/ ");
				}
			}
			System.out.println();
			if(i%3==0){
				System.out.println();
			}
		}
	}
}
import java.util.HashMap;
import java.util.Map;

public class math {
    public static int N=3;
    public static int num[]=new int[82];
    public static void main(String[] args) {
        int x[][]={
                {0,2,0,0,0,9,0,1,0,0},
                {5,0,6,0,0,0,3,0,9,0},
                {0,8,0,5,0,2,0,6,0,0},
                {0,0,5,0,7,0,1,0,0,0},
                {0,0,0,2,0,8,0,0,0,0},
                {0,0,4,0,1,0,8,0,0,0},
                {0,5,0,8,0,7,0,3,0,0},
                {7,0,2,3,0,0,4,0,5,0},
                {0,4,0,0,0,0,0,7,0,0},
        };

        function(x,0,0);
		show_changed();
    }

    public static void function(int[][] x, int r, int c) {
        if (r>=x.length) {
            show(x);
            return;
        }
        if (c==0&&(r==x.length/N||r==x.length/N*2||r==x.length)) {
            if (!checkedbox(x,r)) {
                return;
            };

        }
        if (c>=x.length) {
            function(x, r+1, 0);
            return;
        }

        if (x[r][c]==0) {
            for (int i = 1; i <= x.length; i++) {
                if (checked(x,r,c,i)) {
                    x[r][c]=i;
                    function(x, r, c+1);
                    x[r][c]=0;
                }
            }
        }else{
            function(x, r, c+1);
        }
    }
    public static boolean checkedbox(int[][] x, int r) {
        for (int k = 0; k < x.length; k+=x.length/N) {
            Map<Integer, Integer> map=new HashMap<>();
            for (int i = r-N; i < r; i++) {
                for (int j = k; j < k+x.length/N; j++) {
                    if (map.containsKey(x[i][j])) {
                        return false;
                    }
                    map.put(x[i][j], 1);
                }
            }

        }
        return true;
    }

    private static boolean checked(int[][] x, int r, int c, int i) {
        for (int j = 0; j < x.length; j++) {
            if (x[j][c]==i) {
                return false;
            }
            if (x[r][j]==i) {
                return false;
            }
        }
        return true;
    }

    public static void show(int[][] x) {
    	int k=1;
        for (int i = 0; i < x.length; i++) {
            for (int j = 0; j < x.length; j++) {
				num[k]=x[i][j];
				k++;
            }
        }
    }

	public static void show_changed(){
		for(int i=1;i<=81;i++){
			System.out.print(num[i]+" ");
			if(i%3==0){
				System.out.print("/ ");
			}
			if(i%9==0){
				System.out.println();
			}
			if(i%27==0){
				System.out.println();
			}
		}
	}
}*/
//import java.awt.Color;
import java.util.Random;

/*�ͦ��H���ƿW
 * �y�{�G
 * 1.�ͦ�9*9�ťռƿW�Ʋ�
 * 2.�H�����ƿW�Ʋն��
 * 3.DFS�ͦ��ƿW�зǸѡ]�Y�ƿW�Ʋ�81�Ӯ�l���񺡼Ʀr�^
 * 4.����n�ӪšA������Ū��ƿW�i��D�ѡ]�����^
 * 5.�N�Ҧ��ѻP�зǸѶi����A�N���@�˪��a��O���U�C�o�Ǧa�褣���\�Q�Q����
 */

class MakeSDK {
	private int[][] Arr;//�{�ɼƲ�
	private int [][]Sudoku;
	private int [][]Answer;//����
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
		//�����Ʋ��ح��H����t�Ӽ�
		while(t<15){//t���y�h�A�_�h�B��_�ӯӶO�ɶ��Ft�]���y�֡A�_�h�ͦ����C���ݰ_�Ӥ@�I�]���H��
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

	//�P�_�ӼƦr��g���a��O�_�ŦX�ƿW�W�h
	private boolean isTrue(int arr[][],int x,int y,int num){//�Ʀr��y�СF�Ʀr�a�y�СF�Ʀr�ƭ�
		//�P�_���椸��]3*3�^
		for(int i=(y/3)*3;i<(y/3+1)*3;++i){
			for(int j=(x/3)*3;j<(x/3+1)*3;++j){
				if(arr[i][j]==num){return false;}
			}
		}
		//�P�_���
		for(int i=0;i<9;++i){
			if(arr[i][x]==num || arr[y][i]==num){
				return false;
			}
		}
		return true;
	}

	//�`���u���j���M��
	//���靈�ܦh�ظѪk�A���O�ڭ̥u�ݭn�Ĥ@�ӸѥX�Ӫ�
	private boolean flag=false;//�P�_�O�_�o�X�@�Ӹ�
	int total=0;
	private void DFS(int arr[][],int n,boolean all){//arr�O�ƿW�ƲաAn�O�����`�ס]�@�@81�Ӯ�l�A�`��?81,n?0~80�^�A�O�_�n�D������
		//n/9?��l���a�y�СAn%9?��l����y��
		if(n<81){
			//�p�G�w�g�D�X�F�@�ظѡA�פ�k�N��F�A�����~��D�U�h�F
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
				System.out.println("\n��"+total);
				int k=1;
				for(int i=0;i<9;++i){
					for(int j=0;j<9;++j){
						System.out.print(arr[i][j]);
						if(arr[i][j]!=Answer[i][j]){
							Game[i][j]=Answer[i][j];
						}
						answer[k]=arr[i][j];
						k++;
					}
					System.out.println();
				}
				System.out.println("\n");
			}
		}
	}


	//���ƿW����
	//�O�ҶȦ��@��
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

	//����̲׼ƿW
	public int[][] getArr(){
		return this.Game;
	}

	//����ƿW����
	public int[][] getAnswer(){
		return this.Answer;
	}
}

public class math {
	public static void main(String arg[]){
		MakeSDK game=new MakeSDK();
		System.out.println("�ͦ��D�ءG");
		for(int i=0;i<9;++i){
			for(int h=0;h<9;++h){
				System.out.print(game.getArr()[i][h]);
			}
			System.out.println();
		}

		System.out.println("\n");
		for(int i=1;i<=81;i++){
			System.out.print(game.answer[i]+" ");
			if(i%3==0){
				System.out.print("/ ");
			}
			if(i%9==0){
				System.out.println();
			}
			if(i%27==0){
				System.out.println();
			}
		}
	}
}