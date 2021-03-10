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
print(dati)
print(dati.iloc[0][0])
