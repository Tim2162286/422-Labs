package edu.asupoly.ser422.cluegame.Controller;

import edu.asupoly.ser422.cluegame.Model.GameInfo;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class Controller extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        System.out.println(session);
        RequestDispatcher dispatcher;
        if(session==null) {
            resp.sendRedirect("/");
            return;
        }
        req.getRequestDispatcher("/WEB-INF/View/guess.jsp").forward(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Creates a new session if one does not exist
        HttpSession session = req.getSession(true);
        // If a new session, generate player's cards and computer's cards and add them to the current session
        // Additionally, randomly generate a winning secret and add that to the current session
        if (session.isNew()){
            session.setAttribute("playerName", req.getParameter("name"));
            System.out.println("New Session");
            session.setAttribute("game", new GameInfo());
            req.getRequestDispatcher("/WEB-INF/View/guess.jsp").forward(req,resp);
            return;
        }
        GameInfo game = (GameInfo)session.getAttribute("game");
        game.newPlayerGuess(req.getParameter("suspect"),req.getParameter("room"),req.getParameter("weapon"));
        if(game.getGuessHistory().contains(game.getPlayerGuess())){
            resp.setStatus(HttpServletResponse.SC_OK);
            req.getRequestDispatcher("/WEB-INF/View/repeatGuess.jsp").forward(req,resp);
            return;
        }
        if(game.getPlayerGuess().equals(game.getWinningSecret())){
            resp.setStatus(HttpServletResponse.SC_OK);
            req.getRequestDispatcher("/WEB-INF/View/guessResult.jsp").forward(req,resp);
            return;
        }
        game.addGuess(game.getPlayerGuess());
        game.newComputerGuess();
        req.getRequestDispatcher("/WEB-INF/View/guessResult.jsp").forward(req,resp);
    }
}
