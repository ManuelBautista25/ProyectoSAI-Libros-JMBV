/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agents;

/**
 *
 * @author manuelbautista
 */
import jade.core.Agent;
import behaviours.RequestPerformer;
import gui.BookBuyerGui;
import jade.core.AID;
import jade.core.behaviours.*;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;

public class BookBuyerAgent extends Agent {

    private String bookTitle;
    private AID[] sellerAgents;
    private int ticker_timer = 10000;
    private BookBuyerAgent this_agent = this;
    private BookBuyerGui gui;

    protected void setup() {
        System.out.println("Buyer agent " + getAID().getName() + " is ready");

        gui = new BookBuyerGui(this);
        gui.showGui();

    }

    protected void takeDown() {
        System.out.println("Buyer agent " + getAID().getName() + " terminating");
    }

    public AID[] getSellerAgents() {
        return sellerAgents;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public void tryToBuy(String book) {

        if (book != null && book.length() > 0) {
            bookTitle = book;
            System.out.println("Book: " + bookTitle);

            addBehaviour(new TickerBehaviour(this, ticker_timer) {
                @Override
                protected void onTick() {
                    System.out.println("Trying to buy " + bookTitle);

                    DFAgentDescription template = new DFAgentDescription();
                    ServiceDescription sd = new ServiceDescription();
                    sd.setType("book-selling");
                    template.addServices(sd);

                    try {
                        DFAgentDescription[] result = DFService.search(myAgent, template);
                        System.out.println("Found the following seller agents:");
                        sellerAgents = new AID[result.length];
                        for (int i = 0; i < result.length; i++) {
                            sellerAgents[i] = result[i].getName();
                            System.out.println(sellerAgents[i].getName());
                        }

                    } catch (FIPAException fe) {
                        fe.printStackTrace();
                    }

                    myAgent.addBehaviour(new RequestPerformer(this_agent));
                }
            });
        } else {
            System.out.println("No target book title specified");
            doDelete();
        }
    }
}
