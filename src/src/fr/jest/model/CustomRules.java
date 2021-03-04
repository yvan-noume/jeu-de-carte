package fr.jest.model;

import java.util.Scanner;
/**
 * La documentation est la même que celle de {@link JestEvalMethod}
 * 
 * Pour cette configuration on a :
 * <ul>
 * <li>les DIAMONDS à points négatifs  :  définie par l'Utilisateur <br></li>
 * <li>la règle entre le les HEARTS et le JOKER  :  définie par l'Utilisateur<br> </li>
 * <li>la règle de la paire noire : définie par l'Utilisateur<br></li>
 * <li>la règle sur les As :  définie par l'Utilisateur<br></li>
 * <li>la valeur  : définie par l'Utilisateur<br></li>
 *</ul>
 * 
 *@author Jeff Jordan Tchelong Nwabo - Université de Technologie de Troyes - Réseau et Télécommunication Semestre 01- Automne 2019 - LO02 
 *@see JestEvalMethod
 *@see RegularCard
 *@see Jest
 */
public class CustomRules extends JestEvalMethod {
	private   Scanner sc = new Scanner(System.in);
	/**
	 * Definie un Object de Type {@link JestEvalMethod} , les membres de cet valeurs sont set par l'utilisateur au cours de l'instanciatin de l'objet
	 * <br>
	 */
	public CustomRules() {
		System.out.println("\t\t-->Diamonds count Negative ?           \t\t --> 1 == YES \t\t ---> 0 == NO");
		System.out.print("\t\t\t Your Answer :: ");
		this.negativeDiamonds = readChoice(0 , 1) ;
		System.out.println("\t\t-->Apply the \"Heart And Joker\" Rule ?\t\t --> 1 == YES \t\t ---> 0 == NO");
		System.out.print("\t\t\t Your Answer :: ");
		this.heartsAndJokerRule = readChoice(0 , 1) ; 
		System.out.println("\t\t-->Apply the \"black pair\" rule ?     \t\t --> 1 == YES \t\t ---> 0 == NO");
		System.out.print("\t\t\t Your Answer :: ");
		this.blackPairRule = readChoice(0 , 1)  ;
		System.out.println("\t\t-->Apply the \"Ace\" rule ?             \t\t --> 1 == YES \t\t ---> 0 == NO");
		System.out.print("\t\t\t Your Answer :: ");
		this.aceRule=readChoice(0 , 1) ;
		System.out.println("\t\t-->Which Valu efor the Joker ? [ max Value = 5 Points]");
		System.out.print("\t\t\t Your Answer :: ");
		this.jokerValue = readChoice(0 , 5) ;
	}
	/**
	 * Cette methode permet de récuper une saisie de l'utilsateur pour configurer les règle de la partie
	 * @param includedMinValue la valeur minimal que peut choisir l'utilisateur
	 * @param includedMaxValue la valeur maximale que peut choisir l'utilisateur
	 * @return un entier qui correspond à l'état d'application de la règle en question
	 */
	private  int readChoice(int includedMinValue , int includedMaxValue) {
		String asciiChoice = "" ;
		int    intChoice = -1;
		while (intChoice<includedMinValue || intChoice>includedMaxValue) {
			try { 	asciiChoice=sc.nextLine();
					intChoice = Integer.parseInt(asciiChoice);
					if(intChoice<includedMinValue || intChoice>includedMaxValue ) {
						System.out.println("\tplease enter an Integer in [ "+includedMinValue+" ; "+includedMaxValue+" ]");
						intChoice=-1;
					}

				 }catch(NumberFormatException e) {
						System.out.println("\tplease enter an Integer in [ "+includedMinValue+" ; "+includedMaxValue+" ]");
				  intChoice=-1;

				 }
		}
		return intChoice;
	}
	/**
	 * Determine la valeur du Jest du Joueur cible<br>
	 * @see JestEvalMethod
	 * @return la valeur du Jest en appliquant les {@link CustomRules}
	 */
	public int evalJest(Player targetPlayer) {
		Jest clone = targetPlayer.getJest().cloneJest();
		
		int jestValueResult=0;
		boolean jokerFlag = this.hasJoker(clone) ;
			if(this.heartsAndJokerRule!=1)
				jokerFlag=false;
		int numberOfBlackPair=this.blackPairRule*this.checkBlackPairs(clone);
			if(this.aceRule==1)
				this.checkAces(clone);
		
		this.setDiamondsValues(clone);
		this.checkJokerAndHearts(clone,jokerFlag);
			for(int i=0;i<clone.getMyJestCards().size();i++) {
				jestValueResult+=clone.getMyJestCards(i).getValue();
				
			}
			jestValueResult+=(numberOfBlackPair*2);
			
		//System.out.println("[DEBUG] "+targetPlayer.getName()+"'S JEST WORTHS "+jestValueResult+" POINTS");
		clone.setJestValue(jestValueResult);
		targetPlayer.getJest().setJestValue(jestValueResult);
		return jestValueResult;
	}
	
	public void checkAces(Jest targetJest) {
			int i , j ;
			
			for (i=0 ;i < targetJest.getMyJestCards().size() ;i++ ) {
					if(targetJest.getMyJestCards(i) != null) {
							if(targetJest.getMyJestCards(i).getValue()==1 || targetJest.getMyJestCards(i).getValue()==5) {
							boolean isGoodAce = true ;
								for(j=0;j<targetJest.getMyJestCards().size();j++) {
									if(targetJest.getMyJestCards(j) != null) {
										if(targetJest.getMyJestCards(j).getColour().equals(targetJest.getMyJestCards(i).getColour()) && i!=j ) {
											isGoodAce=false;
										}
									}
								}
								if(isGoodAce) {
									targetJest.getMyJestCards(i).setValue(5);
								}else {
									targetJest.getMyJestCards(i).setValue(1);
								}		
							}	
					}
			}	
	}
	
	public int checkBlackPairs(Jest targetJest) {
		int numberOfBlackPairs = 0 ;
			for(int i = 0; i<targetJest.getMyJestCards().size();i++) {
				if(targetJest.getMyJestCards(i)!=null){
					for(int j=0 ;j<targetJest.getMyJestCards().size();j++) {
						if(targetJest.getMyJestCards(j)!=null) {
							if( (targetJest.getMyJestCards(i).getValue()==targetJest.getMyJestCards(j).getValue()) && targetJest.getMyJestCards(i).getColour().equals(CardColour.CLUBS) && targetJest.getMyJestCards(j).getColour().equals(CardColour.SPADES) &&(i!=j) ) {
								numberOfBlackPairs++;
							}
						}
					}
					
				}
			}
		
		
		return numberOfBlackPairs;
	}
	
	public boolean hasJoker(Jest targetJest) {
		boolean result = false ;
			for(int i = 0 ; i< targetJest.getMyJestCards().size(); i++) {
				if(targetJest.getMyJestCards(i)!=null) {
					if(targetJest.getMyJestCards(i).getColour().equals(CardColour.JOKER)) {
						result=true;
						break;
					}
				}		
			}
		return result;
	}
	
	public void checkJokerAndHearts(Jest targetJest,boolean pHasJoker) {
		int numberOfHearts=this.countHearts(targetJest);
		if(pHasJoker) {
			if(numberOfHearts==0) {
				for(int i=0 ; i<targetJest.getMyJestCards().size() ;i++) {
					if(targetJest.getMyJestCards(i)!=null) {
						if(targetJest.getMyJestCards(i).getColour().equals(CardColour.JOKER)) {
							targetJest.getMyJestCards(i).setValue(this.jokerValue);
						}
					}
				}
			}else if(numberOfHearts<4) {
				for(int i=0 ; i<targetJest.getMyJestCards().size() ;i++) {
					if(targetJest.getMyJestCards(i)!=null) {
						if(targetJest.getMyJestCards(i).getColour().equals(CardColour.HEARTS)) {
							targetJest.getMyJestCards(i).setValue((-1)*targetJest.getMyJestCards(i).getValue());
						}
					}
				}
			}
			
		}else {
			for(int i=0 ; i<targetJest.getMyJestCards().size() ;i++) {
					if(targetJest.getMyJestCards(i).getColour().equals(CardColour.HEARTS)) {
						targetJest.getMyJestCards(i).setValue(0);
					}	
			}
			for(int i=0 ; i<targetJest.getMyJestCards().size() ;i++) {
					if(targetJest.getMyJestCards(i).getColour().equals(CardColour.JOKER)) {
						targetJest.getMyJestCards(i).setValue(this.jokerValue);
					}
				}
		}
	}

	public int countHearts(Jest targetJest) {
		int result =0;
		for(int i=0 ; i<targetJest.getMyJestCards().size() ;i++) {
			if(targetJest.getMyJestCards(i)!=null) {
				if(targetJest.getMyJestCards(i).getColour().equals(CardColour.HEARTS)) {
					result++;
				}
			}
		}
		return result;
	}
	
	public void setDiamondsValues(Jest targetJest){
		for(int i=0 ; i<targetJest.getMyJestCards().size() ;i++) {
			if(targetJest.getMyJestCards(i)!=null) {
				if(targetJest.getMyJestCards(i).getColour().equals(CardColour.DIAMONDS)) {
					targetJest.getMyJestCards(i).setValue(((int)Math.pow(-1, (double)this.negativeDiamonds))*targetJest.getMyJestCards(i).getValue());
				}
			}
		}
	}
	
	
}
