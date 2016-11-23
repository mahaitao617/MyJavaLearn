package castle;

import java.util.Scanner;
import java.util.*;
public class Game {
    private Room currentRoom;
    private HashMap<String,Handler>handlers = new HashMap<String,Handler>();   
    public Game() 
    {
		//handlers.put("go",new HandlerGo());
    	handlers.put("go",new HandlerBye(null));
    	handlers.put("help",new HandlerHelp(null));
        createRooms();
    }

    private void createRooms()
    {
        Room outside, lobby, pub, study, bedroom;
      
        //	制造房间
        outside = new Room("城堡外");
        lobby = new Room("大堂");
        pub = new Room("小酒吧");
        study = new Room("书房");
        bedroom = new Room("卧室");
        
        //	初始化房间的出口
        outside.setExit("east", lobby);
		outside.setExit("south", study);
		outside.setExit("west", pub);
        lobby.setExit("west", outside);
		lobby.setExit("south", outside);
		pub.setExit("east",outside);
		study.setExit("north",outside);
		study.setExit("east",bedroom);
		bedroom.setExit("west",study);
        lobby.setExit("up",pub);
		pub.setExit("down",lobby);
    }
	public void showPrompt()
	{
		System.out.println("现在你在" + currentRoom);
        System.out.print("出口有：");
        System.out.print(currentRoom.getExitDesc());
        System.out.println();
	}

    private void printWelcome() {
        System.out.println();
        System.out.println("欢迎来到城堡！");
        System.out.println("这是一个超级无聊的游戏。");
        System.out.println("如果需要帮助，请输入 'help' 。");
        System.out.println();
        showPrompt();
    }

    private void goRoom(String direction) 
    {
        Room nextRoom = currentRoom.getExit(direction);
        if (nextRoom == null) {
            System.out.println("那里没有门！");
        }
         else {
             currentRoom = nextRoom;
            showPrompt();
        }
    }
	
	public static void main(String[] args) {
		
		Game game = new Game();
		game.printWelcome();
		game.play();
	}
	public void play()
	{
		Scanner in = new Scanner(System.in);
		
		 while ( true ) {
        		String line = in.nextLine();
        		String[] words = line.split(" ");
				Handler handler = handlers.get(words[0]);
				String value = "";
				if(words.length>1)value = words[1];
				if(handler!=null)
				{
					handler.doCmd(value);
					if(handler.isBye())
					{
						break;
					}
				}
		  System.out.println("感谢您的光临。再见！");
	        in.close();
        		/* if ( words[0].equals("help") ) {
        			game.printHelp();
        		} else if (words[0].equals("go") ) {
        			game.goRoom(words[1]);
        		} else if ( words[0].equals("bye") ) {
        			break;
        		} */
        }
	}

}
