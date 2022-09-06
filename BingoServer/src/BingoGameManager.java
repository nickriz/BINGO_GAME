
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;



class BingoGameManager implements Runnable{

    // ArrayList with users for the callbacks
    private ArrayList<BingoListener> users_online;

    private int[] all_numbers;
    private int number_of_drawn_numbers;
    private HashSet<Integer> drawn_numbers;

    private int time=10000;
    public BingoGameManager(ArrayList<BingoListener> users_online){
        this.users_online = users_online;

        this.all_numbers = new int[75];

        for(int i=1;i<75;i++){
            this.all_numbers[i] = i + 1;
        }
        this.drawn_numbers = new HashSet<>();

    }

    //It returns the drawn numbers until the moment it is called
    public HashSet<Integer> getDrawnNumbers(){
        return drawn_numbers;
    }


    //It draws a number that has not  been drawn before
    public int drawNumber(){
        Random random = new Random();

        int position;
        int number_drawm;
        do{
        position = random.nextInt(75);
        number_drawm = this.all_numbers[position];
        }
        while(this.drawn_numbers.contains(number_drawm));
        this.drawn_numbers.add(number_drawm);
        return number_drawm;
    }


    //It returns a Bingo Coupon
    public int[][] getBingoCoupon(){
        int[][] bingo_coupon = new int[5][5];
        Random random = new Random();
        ArrayList<Integer> generated_number = new ArrayList<>();
        int random_number;
        // The usage of do-while is to prevent the generation of the same number.
       for(int i=0; i<5; i++){
           do
               random_number =   1 +  random.nextInt(15);
           while(generated_number.contains(random_number));
           bingo_coupon[i][0] = random_number;
           generated_number.add(random_number);

           do
               random_number =  16 +  random.nextInt(30);
           while(generated_number.contains(random_number));
           bingo_coupon[i][1] = random_number;
           generated_number.add(random_number);

           if(i == 2)
               bingo_coupon[i][2] = 0;
           else {
               do
                   random_number = 31 +  random.nextInt(45);
               while (generated_number.contains(random_number));
               bingo_coupon[i][2] = random_number;
               generated_number.add(random_number);
           }

           do
               random_number =  46 +  random.nextInt(60);
           while(generated_number.contains(random_number));
           bingo_coupon[i][3] = random_number;
           generated_number.add(random_number);



           do
               random_number =  61 +  random.nextInt(75);
           while(generated_number.contains(random_number));
           bingo_coupon[i][4] = random_number;
           generated_number.add(random_number);


       }
       return bingo_coupon;
    }

    //Checking if the coupon has BINGO
    public boolean isBingo(int bingo_coupon[][], ArrayList<Integer> numbers_found){
        int[] rows = {0,0,0,0,0};
        int[] columns = {0,0,0,0,0};

        //edges check
        if(numbers_found.contains(bingo_coupon[0][0]) && numbers_found.contains(bingo_coupon[0][4]) && numbers_found.contains(bingo_coupon[4][0]) && numbers_found.contains(bingo_coupon[4][4]))
            return  true;
        //diag check
        int diag = 0;
        for(int i=0; i<5; i++)
            if(numbers_found.contains(bingo_coupon[i][i]))
                diag = diag +1;
        if(diag == 4)
            return true;

        for(int i=0; i<5; i++){


            // columns check
            if(numbers_found.contains(bingo_coupon[i][0]))
                columns[0]= columns[0] + 1;

            if(numbers_found.contains(bingo_coupon[i][1]))
                columns[1]= columns[1] + 1;

            if(numbers_found.contains(bingo_coupon[i][2]))
                columns[0]= columns[0] + 1;

            if(numbers_found.contains(bingo_coupon[i][3]))
                columns[0]= columns[0] + 1;

            if(numbers_found.contains(bingo_coupon[i][4]))
                columns[0]= columns[0] + 1;


            // rows check
            if(numbers_found.contains(bingo_coupon[0][i]))
                rows[0]= rows[0] + 1;

            if(numbers_found.contains(bingo_coupon[1][i]))
                rows[1]= rows[1] + 1;

            if(numbers_found.contains(bingo_coupon[2][i]))
                rows[0]= rows[0] + 1;

            if(numbers_found.contains(bingo_coupon[3][i]))
                rows[0]= rows[0] + 1;

            if(numbers_found.contains(bingo_coupon[4][i]))
                rows[0]= rows[0] + 1;


        }

        // For the Free Space cell
        if(rows[2]==4 || columns[2] == 4)
            return true;

        for(int i=0; i<5; i++)
            if(rows[i] == 5 || columns[i] == 5)
                return true;


        return false;
    }


    @Override
    public void run() {
        try{
            while(true){
                System.out.println("New Bingo Draw");

                number_of_drawn_numbers = 0;
                int drawn_number;
                while(number_of_drawn_numbers < all_numbers.length){
                    drawn_number = drawNumber();
                    this.drawn_numbers.add(drawn_number);
                    System.out.println("Drawing number: "+ drawn_number + "--->" + (number_of_drawn_numbers +1)+"/75.");
                    for(BingoListener bingolistener: users_online){
                        try {
                            //sending to Client
                            bingolistener.generateNumber(drawn_number);

                        } catch (RemoteException ex) {
                            Logger.getLogger(BingoGameManager.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    Thread.sleep(time);

                    number_of_drawn_numbers++;
                }
                Thread.sleep(10000);

            }
        }catch (InterruptedException ex) {
            Logger.getLogger(BingoGameManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}