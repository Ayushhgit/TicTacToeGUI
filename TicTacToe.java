import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class TicTacToeGUI
{
    private JFrame frame;
    private JButton[][] button;
    private JLabel statusLabel;
    private JLabel[] scoreLabels;
    private char currentPlayer;
    private char[][] board;
    private int[] scores;

    public TicTacToeGUI()
    {
        frame = new JFrame("TIC TAC TOE");
        frame.setSize(300,350);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        board = new char[3][3];
        currentPlayer = 'X';
        
        JPanel boardPanel = new JPanel(new GridLayout(3,3));
        button = new JButton[3][3];
        for(int row = 0; row < 3; row++)
        {
            for(int col = 0; col < 3; col++)
            {
                button[row][col] = new JButton("");
                button[row][col].setFont(new Font(Font.SANS_SERIF, Font.BOLD, 50));
                final int r = row,c = col;
                button[row][col].addActionListener(e-> moveMade(r,c));
                boardPanel.add(button[row][col]);
            }
        }
        
        JPanel controlPanel = new JPanel(new FlowLayout());
        JButton restartButton = new JButton("RESTART");
        restartButton.addActionListener(e-> restartGame());
        controlPanel.add(restartButton);

        
        JPanel scorePanel = new JPanel(new GridLayout(2, 1));
        scoreLabels = new JLabel[2];
        scores = new int[2];
        for (int i = 0; i < 2; i++) {
            scores[i] = 0;
            scoreLabels[i] = new JLabel("Player " + (char)('X' + i) + " Score: " + scores[i]);
            scorePanel.add(scoreLabels[i]);
        }

        JPanel statusPanel = new JPanel();
        statusLabel = new JLabel("Player " + currentPlayer + "'s turn");
        statusPanel.add(statusLabel);

        frame.add(boardPanel, BorderLayout.CENTER);
        frame.add(controlPanel, BorderLayout.NORTH); 
        frame.add(scorePanel, BorderLayout.WEST); 
        frame.add(statusPanel, BorderLayout.SOUTH);
        frame.setVisible(true);    
    }
    
    private void moveMade(int row, int col)
    {
        if(board[row][col] == '\0')
        {
            board[row][col] = currentPlayer;
            button[row][col].setText(String.valueOf(currentPlayer));
            if (checkWin(currentPlayer)) {
                statusLabel.setText("Player " + currentPlayer + " wins!");
                updateScore(currentPlayer);
                disableButton();
            } else if (checkDraw()) {
                statusLabel.setText("It's a draw!");
                disableButton();
            } else {
                currentPlayer = (currentPlayer == 'X') ? 'O' : 'X';
                statusLabel.setText("Player " + currentPlayer + "'s turn");
            }
        }
    }
    
    private boolean checkWin(char player){
        for(int i = 0; i< 3; i++)
        {
            if((board[i][0] == player && board[i][1] == player && board[i][2] == player) || (board[0][i] == player && board[1][i] == player && board[2][i] == player))
                    {
                        return true;
                    }
        }
            return (board[0][0] == player && board[1][1] == player && board[2][2] == player) ||
                (board[0][2] == player && board[1][1] == player && board[2][0] == player);
    }
    
    private boolean checkDraw(){
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                if (board[row][col] == '\0') {
                    return false;
                }
            }
        }
        return true;
    }
    
    private void disableButton()
    {
        for( int i=0; i<3; i++)
        {
            for(int j=0; j<3; j++)
            {
                button[i][j].setEnabled(false);
            }
        }
    }
    
    private void enableButton()
    {
        for( int i=0; i<3; i++)
        {
            for(int j=0; j<3; j++)
            {
                button[i][j].setEnabled(true);
            }
        }
    }
    
    private void restartGame(){
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = '\0';
                button[i][j].setText("");
            }
        }
        currentPlayer = 'X';
        statusLabel.setText("Player " + currentPlayer + "'s turn");
        enableButton();
    }
    
    private void updateScore(char player) {
        int index = (player == 'X') ? 0 : 1;
        scores[index]++;
        scoreLabels[index].setText("Player " + player + " Score: " + scores[index]);
    }
    
    public static void main(String[] args)
    {
        SwingUtilities.invokeLater(TicTacToeGUI::new);
    }
}
