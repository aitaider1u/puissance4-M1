import java.util.ArrayList;

public class State {

    public enum FinalState {
        NON,
        DRAW_GAME,
        HUMAN_WIN,
        MACHINE_WIN
    }

    private static char SYMBOLE[] = {'X','O'};    // X pour l'humain et O pour la machine
    private int player;
    private char[][] plateau = new char[6][7];

    public State(int player) {
        this.player = player;
    }

    public char[][] getPlateau() {
        return plateau;
    }

    public int getPlayer() {
        return player;
    }

    public void setPlayer(int player) {
        this.player = player;
    }

    public void initialState(){
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 7; j++) {
                plateau[i][j] = ' ';
            }
        }
    }

    public boolean playAnAction(Action action){
        if(action.getColumn() >=7 || action.getColumn()<0)
            return false;
        if (this.plateau[1][action.getColumn()] != ' ')
            return false;

        int index = 0;
        while( index < 6 && this.plateau[index][action.getColumn()] == ' ' ){
            index++;
        }

        this.plateau[index-1][action.getColumn()] = SYMBOLE[this.player];
        this.otherPlayer();
        return true;
    }

    public ArrayList<Action> possibleAction(){
        ArrayList<Action> actions = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            if(this.plateau[1][i] == ' ')
                actions.add(new Action(i));
        }
        return actions;
    }

    public FinalState isAFinalState(){
        for (int i = 0; i <6 ; i++) {
            for (int j = 0; j <7 ; j++) {
                //colonne
                int indexI = ((i-4+1)>=0) ? (i-4+1) : 0;
                int k = 0;
                while(k<4 && indexI <= i && indexI+4 <= 6){
                    k++;
                    if ( plateau[indexI][j] != ' ' && plateau[indexI][j] ==plateau[indexI+1][j]  && plateau[indexI+1][j] == plateau[indexI+2][j]&& plateau[indexI+2][j] == plateau[indexI+3][j])
                        if (plateau[i][j] == SYMBOLE[Constant.HUMAN_INDEX])
                            return FinalState.HUMAN_WIN;
                        else
                            return FinalState.MACHINE_WIN;
                    indexI++;
                }
                //ligne
                int indexJ = ((j-4+1)>=0) ? (j-4+1) : 0;
                k = 0;
                while(k<4 && indexJ <= j  && indexJ+4 <= 7 ){
                    k++;
                    if (  plateau[i][indexJ] != ' ' && plateau[i][indexJ] ==plateau[i][indexJ+1]  && plateau[i][indexJ+1] ==plateau[i][indexJ+2]&& plateau[i][indexJ+2] ==plateau[i][indexJ+3])
                        if (plateau[i][j] == SYMBOLE[Constant.HUMAN_INDEX])
                            return FinalState.HUMAN_WIN;
                        else
                            return FinalState.MACHINE_WIN;
                    indexJ++;
                }
                //diagonales
                //1
                int delta_i = i - (((i-4+1)<=0) ? (i-4+1) : 0);
                int delta_j = (((j+4-1)<=6) ? (j+4-1) : 6) - j;
                int delta = Math.min(delta_i,delta_j);
                indexI = i - delta;
                indexJ = j + delta;

                while(k<4 && indexI >= i && indexI+4 <= 6 && indexJ >= j && indexJ-4 >=0){
                    k++;
                    if (  plateau[indexI][indexJ] != ' ' && plateau[indexI][indexJ] ==plateau[indexI+1][indexJ-1]  && plateau[indexI+1][indexJ-1] ==plateau[indexI+2][indexJ-2]&& plateau[indexI+2][indexJ-2] ==plateau[indexI+3][indexJ-3])
                        if (plateau[i][j] == SYMBOLE[Constant.HUMAN_INDEX])
                            return FinalState.HUMAN_WIN;
                        else
                            return FinalState.MACHINE_WIN;
                    indexI++;
                    indexJ--;
                }
                //2
                delta_i = i - (((i-4+1)<=0) ? (i-4+1) : 0);
                delta_j = j - (((j-4+1)>=0) ? (j-4+1) : 0);
                delta = Math.min(delta_i,delta_j);
                indexI = i - delta;
                indexJ = j - delta;
                while(k<4 && indexI <= i  && indexI+4 <= 6   && indexJ >= j  && indexJ+4 <= 7){
                    k++;
                    if (  plateau[indexI][indexJ] != ' ' && plateau[indexI][indexJ] ==plateau[indexI+1][indexJ+1]  && plateau[indexI+1][indexJ+1] ==plateau[indexI+2][indexJ+2]&& plateau[indexI+2][indexJ+2] ==plateau[indexI+3][indexJ+3])
                        if (plateau[i][j] == SYMBOLE[Constant.HUMAN_INDEX])
                            return FinalState.HUMAN_WIN;
                        else
                            return FinalState.MACHINE_WIN;
                    indexI++;
                    indexJ++;
                }
            }
        }
        // si match nul
        int cpt = 0;
        for (int l = 0; l < 7; l++) {
            if (this.plateau[1][l] != ' ')
                cpt++;
        }
        if (cpt == 7)
            return FinalState.DRAW_GAME;
        return FinalState.NON;
    }

    public State cloneState(){
        State clonedState  = new State(this.player);
        char[][] plateauClone = clonedState.getPlateau();
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 7; j++) {
                plateauClone[i][j] = this.plateau[i][j];
            }
        }
        return clonedState;
    }

    public void   otherPlayer(){
         this.player= 1 - this.player;
    }

    public void displayGame(){
        System.out.print("   |");
        for (int j = 0; j < 7; j++) {
            System.out.print(" "+j+" |");
        }
        System.out.println();
        for (int j = 0; j < 8; j++) {
            System.out.print("----");
        }
        System.out.println();
        for (int i = 0; i < 6; i++) {
            System.out.print(" "+i+" |");
            for (int j = 0; j < 7; j++) {
                if (this.plateau[i][j] == SYMBOLE[0]){
                    System.out.print (" ");
                    System.out.print(Constant.ANSI_GREEN + this.plateau[i][j]  + Constant.ANSI_RESET);
                    System.out.print (" |");
                }
                else if (this.plateau[i][j] == SYMBOLE[1]){
                    System.out.print (" ");
                    System.out.print(Constant.ANSI_RED + this.plateau[i][j]  + Constant.ANSI_RESET);
                    System.out.print (" |");
                }else {
                    System.out.print(" "+this.plateau[i][j] + " |");

                }
            }
            System.out.println();
            for (int j = 0; j < 8; j++) {
                System.out.print("----");
            }
            System.out.println();
        }
    }

}