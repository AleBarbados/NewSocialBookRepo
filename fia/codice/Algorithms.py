import matplotlib.pyplot as plt
import pandas as pd
from pandas import ExcelWriter
from pandas import ExcelFile
import random

from sklearn import metrics
from sklearn.cluster import DBSCAN
from sklearn.preprocessing import StandardScaler
from sklearn.preprocessing import normalize
from sklearn.decomposition import PCA
import numpy as np
from mpl_toolkits.mplot3d import Axes3D
from sklearn.cluster import KMeans


# Selezionare il foglio in base alla sua posizione
dati = pd.read_csv(r'/home/alessia/NewSocialBookRepo/fia/codice/dataset.txt', delimiter="\t")


# Creiamo dizionario generi
genre_dic = {
    'Fumetti': 1,
    'Avventura': 4,
    'Fantasy': 6,
    'Fantascienza': 10,
    'Horror': 15,
    'Psicologico ': 20,
    'Psicologico': 20,
    'Thriller': 22,
    'Giallo': 24,
    'Gangster': 26,
    'Drammatico': 34,
    'Romantico': 38,
    'Poesia': 40,
    'Classici': 42,
    'Storico': 45,
    'Saggi': 48,
    'Scienza': 50,
    'Romanzo di formazione': 56,
    'Motivazionale': 57,
    'Attualit� ': 60,
    'Satira': 62

}

# Creiamo dizionario hobby
hobby_dic = {
    'Videogiochi': 1,
    'Serie tv/film': 4,
    'Leggere': 8,
    'Arte': 10,
    'Suonare': 12,
    'Cantare': 14,
    'Ballare': 18,
    'Sport': 24,
    'Sport ': 24,
    'Viaggiare': 34,
    'Volontariato': 38,
    'Cucinare': 46,
    'Beauty': 50
}

# Creiamo dizionario del numero di libri medio che l'utente legge durante un anno
libri_dic = {
    'Nessuno': 0,
    'Tra 1 e 3': 1,
    'Tra 3 e 5': 2,
    'Pi� di 5 ': 3,
    ' Pi� di 5': 3,
    'Pi� di 5': 3
}



# Questa funzione produce un dataframe di valori pesati
def discretizzazione(dic, i, dati):
    if(i == 0):
        j = 0
        colonna = "Quali sono i tuoi hobby?"

    else:
        j = 2
        colonna = "Quali sono i tuoi generi preferiti?"

    len1 = len(dati)
    for row in range(len1):

        # Reperiamo per la riga i-esima gli hobby/ i generi
        data_cell = dati.loc[row][j]
        # data_cell restituisce una stringa e con lo split prendiamo una lista degli hobby/generi
        data_value = data_cell.split(", ")
        leng = len(data_value)
        # Rendiamo i valori omogenei
        single_value = choice(leng, dic, data_value, i)
        # Nel nostro dataframe inseriamo al posto del genere/hobby il valore calcolato
        dati.at[row, colonna] = single_value


def choice(leng, dic, data_value, i):
    if(leng == 1):
        return one(dic, data_value, i)
    elif(leng == 2):
        return two(dic, data_value, i)
    elif(leng == 3):
        return three(dic, data_value, i)
    elif(leng == 4):
        return four(dic, data_value, i)
    elif(leng == 5):
        return five(dic, data_value, i)
    else:
        return six(dic, data_value)



def one(dic, data_value, i):
    single_value = dic[data_value[0]] * (5 + i)
    return single_value


def two(dic, data_value, i):
    single_value = dic[data_value[0]] * (3 + i) + dic[data_value[1]] * 2

    return single_value


def three(dic, data_value, i):
    single_value = dic[data_value[0]] * (2 + i) + dic[data_value[1]] * 2 + dic[data_value[2]]
    return single_value


def four(dic, data_value, i):
    single_value = dic[data_value[0]] * (2 + i) + dic[data_value[1]] + dic[data_value[2]] + dic[data_value[3]]
    return single_value


def five(dic, data_value, i):
    single_value = dic[data_value[0]] * (i + 1) + dic[data_value[1]] + dic[data_value[2]] + dic[data_value[3]] + dic[data_value[4]]
    return single_value


def six(dic, data_value):
    single_value = dic[data_value[0]] + dic[data_value[1]] + dic[data_value[2]] + dic[data_value[3]] + dic[data_value[4]] + dic[data_value[5]]
    return single_value

# Sostituiamo le stringhe con il loro valore associato
def pesoLibri(dati):
    len1 = len(dati)
    for row in range(len1):
        dati.iat[row, 1] = libri_dic[dati.iloc[row]["Quanti libri leggi all'anno?"]]

### INIZIA FASE CLUSTERING ###
def clustering():

    # Calcolo del peso degli hobby, generi e libri letti per ogni singolo utente

    pesoLibri(dati)
    discretizzazione(hobby_dic, 0, dati)
    discretizzazione(genre_dic, 1, dati)

    # Normalizziamo e standardizziamo i dati che vengono scalati per una migliore distribuzione
    scaler = StandardScaler()
    X_scaled = scaler.fit_transform(dati)

    # Normalizzazione dei dati in modo tale che la distribuzione dei dati assomigli ad una distribuzione gaussiana
    X_normalized = normalize(X_scaled)

    # Conversione da un panda dataframe ad un numpyArray
    X_normalized = pd.DataFrame(X_normalized)

    # Stampa dei dati scalati e normalizzati ->   print(X_normalized)


    ###  INIZIO PCA ###

    pca = PCA(n_components = 2)

    # Esecuzione algoritmo
    X_principal = pca.fit_transform(X_normalized)

    # Conversione a dataFrame di pandas
    X_principal = pd.DataFrame(X_principal)

    # Nome degli headers delle colonne
    X_principal.columns = ['P1', 'P2']

    # Stampa dati ridotti da PCA ->    print(X_principal)


    ### INIZIO DBSCAN ###

    # Passaggio dei dati all'algoritmo DBSCAN, la funzione accetta un array di dati con caratteristiche numeriche
    # Parametro eps: Distanza massima tra due campioni per essere considerati nello stesso vicinato
    # Parametro min_samples: numero minimo di punti per considerare un intorno di un punto denso
    db_default = DBSCAN(eps = 0.2, min_samples = 4).fit(X_principal)

    # La funzione ritorna un array di zeri dello stesso tipo e forma dell'array dato, uso per rappresentazione grafica
    core_samples_mask = np.zeros_like(db_default.labels_, dtype = bool)

    # Con questa istruzione in una matrice numpy vengono tracciati i dati del dataset che sono
    # punti densi, i valori che non vengono settati a true significa che sono punti di rumore
    core_samples_mask[db_default.core_sample_indices_] = True

    # Settiamo variabile lables (array nel quale in ogni cella (associazione con ciascun dato) viene inserita una label che indica il cluster di appartenenza)
    labels = db_default.labels_


   ### Da qui recuperiamo a quale cluster un lettore appartiene ###
    cluster_dic_list = [{

    }]

    # Con questo ciclo per ogni lettore presente nel dataset recuperiamo il cluster a cui appartiene
    # I labels sono ordinati allo stesso modo del dataset quindi il labels[0] contiene il cluster del dati.loc[0][0]
    for c_row in range(len(dati)):
      cluster_dic_list.append({"dato_numero" : c_row,"ClusterNumber" : labels[c_row]})


    # Calcolo del numero dei cluster senza considerare i punti di rumore
    n_clusters_ = len(set(labels)) - (1 if -1 in labels else 0)

    # Calcolo dei punti di rumore
    n_noise_ = list(labels).count(-1)

    print("############################\nRISULTATI DBSCAN\n########################")
    print('Numero di cluster stimati: %d' % n_clusters_)
    print('Numero di punti di rumore: %d' % n_noise_)
    print("Coefficiente di forma: %0.3f" % metrics.silhouette_score(dati, labels))


    ### INIZIO RAPPRESENTAZIONE GRAFICA DBSCAN ###
    # Codice reperito dalla guida ufficiale della libreria sklearn per la rappresentazione del clustering
    unique_labels = set(labels)
    colors = [plt.cm.Spectral(each)
              for each in np.linspace(0, 1, len(unique_labels))]
    for k, col in zip(unique_labels, colors):
        if k == -1:
            # Black used for noise.
            col = [0, 0, 0, 1]

        class_member_mask = (labels == k)

        xy = dati[class_member_mask & core_samples_mask]
        plt.plot(xy.iloc[:, 0], xy.iloc[:, 1], 'o', markerfacecolor = tuple(col),
                 markeredgecolor = 'k', markersize = 14)

        xy = dati[class_member_mask & ~core_samples_mask]
        plt.plot(xy.iloc[:, 0], xy.iloc[:, 1], 'o', markerfacecolor = tuple(col),
                 markeredgecolor = 'k', markersize = 6)

    plt.title('DBSCAN Numero di cluster stimati: %d' % n_clusters_)
    plt.show()
    ### FINE RAPPRESENTAZIONE GRAFICA DBSCAN ###
    ### FINE DBSCAN ###


    ### K-MEANS ###

    # Con questa istruzione rendiamo deterministica la posizione iniziale dei 21 centroidi e settiamo il valore del seme = 21
    np.random.seed(21)

    # Otteniamo una variabile k-means
    km = KMeans(n_clusters = 21)

    # Settiamo il titolo della finestra che ospita il grafico come la sua dimensione
    fig = plt.figure("Risultato K-MEANS", figsize = (4, 3))

    # Poichè abbiamo 3 caratteristiche per ogni dato ( Peso hobby, peso genere, numero libri) otteniamo un grafico avente tre dimensioni
    ax = Axes3D(fig, rect = [0, 0, .95, 1], elev = 48, azim = 134)

    # Esecuzione dell'algoritmo sul dataset
    km.fit(dati)

    # Ottenimento dei labes
    labels = km.labels_

    # Dizionario che conterrà l'id dei lettori ed il loro cluster di appartenenza
    kmeans_dic_list = []

    # Ci permette di recuperare tutti i lettori nel cluster del k-means
    for c_row in range(len(dati)):
        kmeans_dic_list.append({"Id": c_row, "ClusterNumber": labels[c_row]})

    # Rappresentazione dei punti nello spazio tridimensionale
    ax.scatter(dati.iloc[:, 2], dati.iloc[:, 0], dati.iloc[:, 1], c=labels.astype(np.float), edgecolor='k')

    ax.w_xaxis.set_ticklabels([])
    ax.w_yaxis.set_ticklabels([])
    ax.w_zaxis.set_ticklabels([])
    ax.set_xlabel('Genere')
    ax.set_ylabel('Hobby')
    ax.set_zlabel('Num_libri')
    title = "K-MEANS"
    ax.set_title(title)
    ax.dist = 12

    # Risultati k-means
    print("\n\n#################### RISULTATI K-MEANS ############################")
    print("Coefficiente di forma %0.3f" % metrics.silhouette_score(dati, labels))

    # Visualizzazione grafico tridimensionale
    plt.show()
    return kmeans_dic_list