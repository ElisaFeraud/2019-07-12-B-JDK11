package it.polito.tdp.food.model;

public class Collegamento {
            Food food1;
            Food food2;
            Double grassiMedi1;
            Double grassiMedi2;
            Double peso;
            Food origine;
            Food destinazione;
			public Collegamento(Food food1, Food food2, Double grassiMedi1, Double grassiMedi2) {
				this.food1 = food1;
				this.food2 = food2;
				this.grassiMedi1 = grassiMedi1;
				this.grassiMedi2 = grassiMedi2;
				if(grassiMedi1>grassiMedi2) {
					peso = grassiMedi1-grassiMedi2;
					origine=food1;
					destinazione= food2;
				}
				else {
					peso = grassiMedi2-grassiMedi1;
					origine=food2;
					destinazione=food1;
				}
			}
			public Food getFood1() {
				return food1;
			}
			public void setFood1(Food food1) {
				this.food1 = food1;
			}
			public Food getFood2() {
				return food2;
			}
			public void setFood2(Food food2) {
				this.food2 = food2;
			}
			public Double getGrassiMedi1() {
				return grassiMedi1;
			}
			public void setGrassiMedi1(Double grassiMedi1) {
				this.grassiMedi1 = grassiMedi1;
			}
			public Double getGrassiMedi2() {
				return grassiMedi2;
			}
			public void setGrassiMedi2(Double grassiMedi2) {
				this.grassiMedi2 = grassiMedi2;
			}
			public Double getPeso() {
				return peso;
			}
			public void setPeso(Double peso) {
				this.peso = peso;
			}
			public Food getOrigine() {
				return origine;
			}
			public void setOrigine(Food origine) {
				this.origine = origine;
			}
			public Food getDestinazione() {
				return destinazione;
			}
			public void setDestinazione(Food destinazione) {
				this.destinazione = destinazione;
			}
			@Override
			public String toString() {
				return origine + "-" + destinazione + " " + peso;
			}
			
            
}
