from Algorithms import *
import random


################### INIZIO MAIN #############

#####FUNZIONI UTILI
def trovaGenere(numero):
    return genere_dic[numero]

def trovaHobby(numero):
    return hobby_dict[numero]

def trovaNumLibri(numero):
    return book_dic[numero]

#ritorna il cluster del film dato in input tramite l'id
def trovaClusterDelNuovoUtente(clusters):
    return clusters[-1].get("ClusterNumber")


def trovaUtentiByCluster(numero_cluster, clustering):
    suggeriti = "utenti suggeriti:\n"
    for row in range(len(clustering)):
        if(clustering[row].get("ClusterNumber") == numero_cluster):
            suggeriti = suggeriti + "utente " + str(row) + "\n"
    return suggeriti


##dizionari per input
#creiamo dizionario generi
genere_dic = {
    1: 'Fumetti',
    2: 'Avventura',
    3: 'Fantasy',
    4: 'Fantascienza',
    5: 'Horror',
    6: 'Psicologico',
    7: 'Thriller',
    8: 'Giallo',
    9: 'Gangster',
    10: 'Drammatico',
    11: 'Romantico',
    12: 'Poesia',
    13: 'Classici',
    14: 'Storico',
    15: 'Saggi',
    16: 'Scienza',
    17: 'Romanzo di formazione',
    18: 'Motivazionale',
    19: 'Attualit� ',
    20: 'Satira'

}

#creiamo dizionario hobby
hobby_dict = {
    1: 'Videogiochi',
    2: 'Serie tv/film',
    3: 'Leggere',
    4: 'Arte',
    5: 'Suonare',
    6: 'Cantare',
    7: 'Ballare',
    8: 'Sport',
    9: 'Sport ',
    10: 'Viaggiare',
    11: 'Volontariato',
    12: 'Cucinare',
    13: 'Beauty'
}

# Creiamo dizionario del numero di libri medio che l'utente legge
book_dic = {
    1: 'Nessuno',
    2: 'Tra 1 e 3',
    3: 'Tra 3 e 5',
    4: 'Pi� di 5'
}
# Variabile risposta, input di default e contatore
risposta = ""
inp = 30
count = 0

print("\n#### Salve Fabio^2!!! \nBenvenuti nel nostro progetto di Socialbook! Produciamo una profilazione migliore dei più grandi social al mondo!!! ####\n")
print("Regole: premere 'invio' ad ogni scelta, premere 0 per confermare le scelte, hai un massimo di 5 hobby e 6 generi\n")
print("Scegli pure i tuoi hobby tra quelli elencati")
print("Videogiochi: 1, Serie tv/film: 2, Leggere: 3, Arte: 4, Suonare: 5, Cantare: 6, Ballare: 7, Sport: 8, Sport: 9, \nViaggiare: 10, Volontariato: 11, Cucinare: 12, Beauty: 13")

# Ciclo per gli hobby
while inp != 0 and count < 5:
    inp = int(input())
    if inp > 0 and inp < 14:
        if count == 0:
            risposta = risposta + trovaHobby(inp)
        else:
            risposta = risposta + ", " + trovaHobby(inp)
        count = count + 1
    elif(inp == 0):
        print("Prossima domanda")
    else:
        print("Risposta out of range")

risposta = risposta + "\t"

# Ciclo per il numero di libri letti

print("Quanti libri leggi mediamente in un anno? Nessuno: 1, tra 1 e 3: 2, tra 3 e 5: 3, più di 5 : 4")
inp = int(input())
risposta = risposta + trovaNumLibri(inp) + "\t"

print("scegli i tuoi generi preferiti\n")
print("Fumetti: 1, Avventura: 2, Fantasy: 3, Fantascienza: 4, Horror: 5, Psicologico : 6, Thriller: 7, Giallo: 8, Gangster: 9, Drammatico: 10,\n")
print("Romantico: 11, Poesia: 12, Classici: 13, Storico: 14, Saggi: 15, Scienza: 16, Romanzo di formazione: 17, Motivazionale: 18, Attualita' : 19,Satira: 20")


# Resettiamo il contatore
count = 0


# Ciclo per i generi
while inp != 0 and count < 6:
    inp = int(input())
    if inp > 0 and inp < 21:
        if count == 0:
            risposta = risposta + trovaGenere(inp)
        else:
            risposta = risposta + ", " + trovaGenere(inp)
        count = count + 1
    elif (inp == 0):
        print("Prossima domanda")
    else:
        print("Risposta out of range")

risposta = risposta + "\n"


# Scriviamo i risultati delle scelte del nuovo lettore nel file
f = open("/home/alessia/NewSocialBookRepo/fia/codice/dataset.txt", "a")
f.write(risposta)
f.close()

# Avviamo l'algoritmo di clustering (il risultato sarà quello del k-means)
clustering = clustering()

# Prende il cluster del nuovo iscritto
cluster_number = trovaClusterDelNuovoUtente(clustering)

# Stampa utenti suggeriti (dello stesso cluster)

stampa = trovaUtentiByCluster(cluster_number, clustering)

print(clustering)
print(stampa)