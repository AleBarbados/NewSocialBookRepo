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


# selezionare il foglio in base alla sua posizione
dati = pd.read_csv(r'/home/alessia/NewSocialBookRepo/fia/codice/dataset.txt', delimiter="\t")

#creiamo dizionario generi
genre_dic = {
    'Fumetti': 1,
    'Avventura': 4,
    'Fantasy': 6,
    'Fantascienza': 10,
    'Horror': 15,
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
    'Attualità': 60,
    'Satira': 62

}

#creiamo dizionario hobby
hobby_dic = {
    'Videogiochi': 1,
    'Serie tv/film': 4,
    'Leggere': 8,
    'Arte': 10,
    'Suonare': 12,
    'Cantare': 14,
    'Ballare': 18,
    'Sport': 24,
    'Viaggiare': 34,
    'Volontariato': 38,
    'Cucinare': 46,
    'Beauty': 50
}

#creiamo dizionario del numero di libri medio che l'utente legge
libri_dic = {
    'Nessuno': 0,
    'Tra 1 e 3': 1,
    'Tra 3 e 5': 2,
    'Più di 5': 3
}




def discretizzazione(dic, i, dati):

    len1 = len(dati)
    for row in range(len1):
        #Reperiamo per la riga i-esima gli hobby
        data_cell = dati.loc[row][0]
        #data_cell restituisce una stringa e con lo split prendiamo una lista degli hobby
        data_value = data_cell.split(", ")
        #Se un utente possiede un singolo hobby allora lo moltiplichiamo 5 volte
        leng = len(data_value)
        print("************iterazione numero %d", row)
        single_value = choice(leng, dic, data_value, i)
        # Nel nostro dataframe inseriamo al posto del genere il valore calcolato
        dati.at[row, "Quali sono i tuoi hobby?"] = single_value


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
    print("*********data value %s", data_value[0])
    print("***********dic di data_value &d", dic[data_value[0]])
    print("******dictionary %s", dic)
    print("indice: %d", i)
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


#Calcolo del peso degli hobby per ogni singolo utente


#dataset_h = discretizzazione(hobby_dic, 0, dati)
discretizzazione(hobby_dic, 0, dati)

print(dati)
