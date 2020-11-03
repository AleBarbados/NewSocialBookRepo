/*
BSD 3-Clause License

Copyright (c) 2019, Mattia De Rosa
All rights reserved.

Redistribution and use in source and binary forms, with or without
modification, are permitted provided that the following conditions are met:

* Redistributions of source code must retain the above copyright notice, this
  list of conditions and the following disclaimer.

* Redistributions in binary form must reproduce the above copyright notice,
  this list of conditions and the following disclaimer in the documentation
  and/or other materials provided with the distribution.

* Neither the name of the copyright holder nor the names of its
  contributors may be used to endorse or promote products derived from
  this software without specific prior written permission.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE
FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*/
package socialBook.model;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Carrello {

		private Map<Integer, Integer> libro_quantita = new HashMap<>(); //associo ad ogni id libro una quantità
		private int id_carrello;
		private Map<Integer, Libro> libri = new HashMap<>(); //associo ad ogni id il libro corrispondente
		private int numLibri = 0;
		private double sumPrezzo = 0;

		public Carrello() {
		}


		public void aggiungiLibro(Libro libro, Integer quantita){

			libro_quantita.put(libro.getId(), quantita);
			libri.put(libro.getId(), libro); //sovrascrivo se già esistente
			numLibri += quantita;
			sumPrezzo += quantita*libro.getPrezzo();
		}

		public void rimuoviLibro(int id_libro, int quantita){
			if(quantita > libro_quantita.get(id_libro)){
				quantita = libro_quantita.get(id_libro); //se voglio rimuovere più oggetti della disponibilità, limito
			}
			libro_quantita.put(id_libro, libro_quantita.get(id_libro)-quantita);
			if(libro_quantita.get(id_libro) <= 0){
				libro_quantita.remove(id_libro);
				libri.remove(id_libro);
			}
			numLibri -= quantita;
			sumPrezzo -= quantita*libri.get(id_libro).getPrezzo();
		}

		public Map<Integer, Integer> getProducts() {
			return libro_quantita;
		}

		public int getId_carrello() {
			return id_carrello;
		}

		public void setId_carrello(int id_carrello) {
			this.id_carrello = id_carrello;
		}

		public int getNumLibri() {
			return numLibri;
		}

		public void setNumProdotti(int numLibri) {
			this.numLibri = numLibri;
		}

		public double getSumPrezzo() {
			return sumPrezzo;
		}

		public void setSumPrezzo(double sumPrezzo) {
			this.sumPrezzo = sumPrezzo;
		}
		public Map<Integer, Integer> getQuantitaProdotti() {
			return libro_quantita;
		}
		public void setQuantitaProdotti(Map<Integer, Integer> quantitaProdotti) {
			this.libro_quantita = quantitaProdotti;
		}
		public Map<Integer, Libro> getProdotti() {
			return libri;
		}
		public void setProdotti(Map<Integer, Libro> libri) {
			this.libri = libri;
		}



}
