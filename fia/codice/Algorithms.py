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
    'Fumetti' : 1,
    'Avventura' : 4,
    'Fantasy' : 6,
    'Fantascienza' : 10,
    'Horror' : 15,
    'Psicologico' : 20,
    'Thriller' : 22,
    'Giallo' : 24,
    'Gangster' : 26,
    'Drammatico' : 34,
    'Romantico' : 38,
    'Poesia' : 40,
    'Classici' : 42,
    'Storico': 45,
    'Saggi' : 48,
    'Scienza' : 50,
    'Romanzo di formazione' : 56,
    'Motivazionale' : 57,
    'Attualità' : 60,
    'Satira' : 62

}
#creiamo dizionario hobby

hobby_dic = {
    'Videogiochi' : 1,
    'Serie tv/film' : 4,
    'Leggere' : 8,
    'Arte' : 10,
    'Suonare' : 12,
    'Cantare' : 14,
    'Ballare' : 18,
    'Sport' : 24,
    'Viaggiare' : 34,
    'Volontariato' : 38,
    'Cucinare' : 46,
    'Beauty' : 50
}

#creiamo dizionario del numero di libri medio che l'utente legge
libri_dic = {
    'Nessuno' : 0,
    'Tra 1 e 3' : 1,
    'Tra 3 e 5' : 2,
    'Più di 5' : 3
}