/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package checkersclient;

import java.util.ArrayList;

/**
 *
 * @author Brandon
 */
public class CheckersBoard {

    public CheckersBoard() {
        for (int i = 1; i < 8; i += 2) {
            board[0][i] = -1;
            board[2][i] = -1;
            board[6][i] = 1;
        }
        for (int i = 0; i < 8; i += 2) {
            board[1][i] = -1;
            board[5][i] = 1;
            board[7][i] = 1;
        }
    }

    public CheckersBoard(CheckersBoard other) {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                this.board[i][j] = other.board[i][j];
            }
        }
    }

    public void move(String move) {
        int numMoves = (move.length() - 3) / 4;
        for (int i = 0; i < numMoves; i++) {
            move(Character.digit(move.charAt(4 * i), 10),
                    Character.digit(move.charAt(2 + 4 * i), 10),
                    Character.digit(move.charAt(4 * (i + 1)), 10),
                    Character.digit(move.charAt(2 + 4 * (i + 1)), 10));
        }

    }

    public void move(int fromRow, int fromCol, int toRow, int toCol) {
        board[toRow][toCol] = board[fromRow][fromCol];
        board[fromRow][fromCol] = 0;
        if (toRow - fromRow == 2 || fromRow - toRow == 2) {
            int jumpedx = (toCol - fromCol) / 2;
            int jumpedy = (toRow - fromRow) / 2;
            board[fromRow + jumpedy][fromCol + jumpedx] = 0;
        }
        if (toRow == 7 && board[toRow][toCol] == -1) {
            board[toRow][toCol] = -2;
        }
        if (toRow == 0 && board[toRow][toCol] == 1) {
            board[toRow][toCol] = 2;
        }
    }

    public int[][] getPiecePositions() {
        return this.board;
    }

    public ArrayList<String> getPossibleMoves(boolean red) {
        ArrayList<String> captures = new ArrayList();
        ArrayList<String> moves = new ArrayList();
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                StringBuilder sb = new StringBuilder();
                if (red) {
                    if (board[row][col] > 0) {
                        if (row > 1 && col > 1 && board[row - 1][col - 1] < 0 && board[row - 2][col - 2] == 0) {
                            int toRow = row - 2;
                            int toCol = col - 2;
                            captures.add(row + ":" + col + "-" + toRow + ":" + toCol);
                        }
                        if (row > 1 && col < 6 && board[row - 1][col + 1] < 0 && board[row - 2][col + 2] == 0) {
                            int toRow = row - 2;
                            int toCol = col + 2;
                            captures.add(row + ":" + col + "-" + toRow + ":" + toCol);
                        }
                        if (row > 0 && col > 0 && board[row - 1][col - 1] == 0) {
                            int toRow = row - 1;
                            int toCol = col - 1;
                            moves.add(row + ":" + col + "-" + toRow + ":" + toCol);
                        }
                        if (row > 0 && col < 7 && board[row - 1][col + 1] == 0) {
                            int toRow = row - 1;
                            int toCol = col + 1;
                            moves.add(row + ":" + col + "-" + toRow + ":" + toCol);
                        }
                        if (board[row][col] == 2) {
                            if (row < 6 && col > 1 && board[row + 1][col - 1] < 0 && board[row + 2][col - 2] == 0) {
                                int toRow = row + 2;
                                int toCol = col - 2;
                                captures.add(row + ":" + col + "-" + toRow + ":" + toCol);
                            }
                            if (row < 6 && col < 6 && board[row + 1][col + 1] < 0 && board[row + 2][col + 2] == 0) {
                                int toRow = row + 2;
                                int toCol = col + 2;
                                captures.add(row + ":" + col + "-" + toRow + ":" + toCol);
                            }
                            if (row < 7 && col > 0 && board[row + 1][col - 1] == 0) {
                                int toRow = row + 1;
                                int toCol = col - 1;
                                moves.add(row + ":" + col + "-" + toRow + ":" + toCol);
                            }
                            if (row < 7 && col < 7 && board[row + 1][col + 1] == 0) {
                                int toRow = row + 1;
                                int toCol = col + 1;
                                moves.add(row + ":" + col + "-" + toRow + ":" + toCol);
                            }
                        }
                    }
                } else {
                    if (board[row][col] < 0) {
                        if (row < 6 && col > 1 && board[row + 1][col - 1] > 0 && board[row + 2][col - 2] == 0) {
                            int toRow = row + 2;
                            int toCol = col - 2;
                            captures.add(row + ":" + col + "-" + toRow + ":" + toCol);
                        }
                        if (row < 6 && col < 6 && board[row + 1][col + 1] > 0 && board[row + 2][col + 2] == 0) {
                            int toRow = row + 2;
                            int toCol = col + 2;
                            captures.add(row + ":" + col + "-" + toRow + ":" + toCol);
                        }
                        if (row < 7 && col > 0 && board[row + 1][col - 1] == 0) {
                            int toRow = row + 1;
                            int toCol = col - 1;
                            moves.add(row + ":" + col + "-" + toRow + ":" + toCol);
                        }
                        if (row < 7 && col < 7 && board[row + 1][col + 1] == 0) {
                            int toRow = row + 1;
                            int toCol = col + 1;
                            moves.add(row + ":" + col + "-" + toRow + ":" + toCol);
                        }
                        if (board[row][col] == -2) {
                            if (row > 1 && col > 1 && board[row - 1][col - 1] < 0 && board[row - 2][col - 2] == 0) {
                                int toRow = row - 2;
                                int toCol = col - 2;
                                captures.add(row + ":" + col + "-" + toRow + ":" + toCol);
                            }
                            if (row > 1 && col < 6 && board[row - 1][col + 1] < 0 && board[row - 2][col + 2] == 0) {
                                int toRow = row - 2;
                                int toCol = col + 2;
                                captures.add(row + ":" + col + "-" + toRow + ":" + toCol);
                            }
                            if (row > 0 && col > 0 && board[row - 1][col - 1] == 0) {
                                int toRow = row - 1;
                                int toCol = col - 1;
                                moves.add(row + ":" + col + "-" + toRow + ":" + toCol);
                            }
                            if (row > 0 && col < 7 && board[row - 1][col + 1] == 0) {
                                int toRow = row - 1;
                                int toCol = col + 1;
                                moves.add(row + ":" + col + "-" + toRow + ":" + toCol);
                            }
                        }
                    }
                }
            }
        }
        if (captures.size() > 0) {
            return captures;
        }
        return moves;
    }

    public double evaluate() {
        double ret = 0.0;
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                if (board[row][col] == 0) {
                    continue;
                }
                ret += board[row][col];
                boolean red = board[row][col] > 0;
                ret += evalHelper(row, col, 1, 1, red);
                ret += evalHelper(row, col, -1, 1, red);
                ret += evalHelper(row, col, 1, -1, red);
                ret += evalHelper(row, col, -1, -1, red);
            }
        }
        return ret;
    }

    private double evalHelper(int row, int col, int relRow, int relCol, boolean red) {
        double ret = 0;
        if (0 <= row + relRow && row + relRow <= 7 && 0 <= col + relCol && col + relCol <= 7) {
            if (red) {
                if (board[row + relRow][col + relCol] < 0) {
                    if ((relRow == -1 || board[row][col] == 2) && 0 <= row + relRow * 2 && row + relRow * 2 <= 7 && 0 <= col + relCol * 2
                            && col + relCol * 2 <= 7 && board[row + relRow * 2][col + relCol * 2] == 0) {
                        ret -= board[row + relRow][col + relCol];
                    } else if ((relRow == -1 || board[row + relRow][col + relCol] == -2) && 0 <= row - relRow && row - relRow <= 7 && 0 <= col - relCol
                            && col - relCol <= 7 && board[row - relRow][col - relCol] == 0) {
                        ret -= board[row][col];
                    }
                }
            } else {
                if (board[row + relRow][col + relCol] > 0) {
                    if ((relRow == -1 || board[row][col] == -2) && 0 <= row + relRow * 2 && row + relRow * 2 <= 7 && 0 <= col + relCol * 2
                            && col + relCol * 2 <= 7 && board[row + relRow * 2][col + relCol * 2] == 0) {
                        ret -= board[row + relRow][col + relCol];
                    } else if ((relRow == -1 || board[row + relRow][col + relCol] == 2) && 0 <= row - relRow && row - relRow <= 7 && 0 <= col - relCol
                            && col - relCol <= 7 && board[row - relRow][col - relCol] == 0) {
                        ret -= board[row][col];
                    }
                }
            }
        }
        return ret;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                sb.append(board[i][j]);
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    private final int[][] board = new int[8][8];
}
