import random
import threading




def gera_vetores(size, matrix):
    vetor = []

    for i in range(0, size):
        vetor.append(random.randint(250, 29500))

    matrix.append(vetor)


def generate_matrix(size):
    matrix = []    


    threads = []
    for i in range(0, size):
        thread = threading.Thread(target=gera_vetores, args=(size,matrix,))
        threads.append(thread)
        thread.start()
        
    for thread in threads:
        thread.join()

    return matrix

def min_concurrent(matrix, resultados, indexMatrix):

    menor = matrix[indexMatrix][0]
    for valor in matrix[indexMatrix]:
        if valor < menor:
            menor = valor

    resultados.append(menor)
     

def min(matrix):
    smallest = float('inf')

    resultados = []

    threads = []
    for i in range(0, len(matrix)):
        thread = threading.Thread(target=min_concurrent, args=(matrix,resultados,i,))
        threads.append(thread)
        thread.start()

    for thread in threads:
        thread.join()

    for valor in resultados:
        if valor < smallest:
            smallest = valor
    
    return smallest


def max_concurrent(matrix, resultados, i):
    maior = matrix[i][0]
    for elemento in matrix[i]:
        if elemento > maior:
            maior = elemento

    resultados.append(maior)
    

def max(matrix):
    largest = float('-inf')
    
    resultados = []
    threads = []
    for i in range(0, len(matrix)):
        thread =  threading.Thread(target=max_concurrent, args=(matrix, resultados, i))
        thread.start()
        threads.append(thread)

    for thread in threads:
        thread.join()

    for valor in resultados:
        if i > largest:
            largest = valor

    return largest



if __name__ == "__main__":
    size = 10

    

    matrix =  generate_matrix(size)
    print(matrix)

    minimoValor = min(matrix)

    print(f"valor minimo = {minimoValor}")

    maximoValor =  max(matrix)

    print(f"valor maximo = {maximoValor}")
