#PCBE

## Cerinta

```
Bursa
Simultati sau implementati o bursa unde se tranzactioneaza actiuni. Actiunile sint ale diverselor companii 
listate la bursa. Tranzactiile se efectueaza de catre cumparatori si respectiv vinzatori de actiuni, in felul urmator:
- vinzatorii pun in vinzare un numar de actiuni si cer un anumit pret per actiune
- cumparatorii isi arata intentia de a cumpara un numar de actiuni, la un anumit pret
- atunci cind o oferta cu o cerere corespund ca pret, se tranzactioneaza un numar de actiuni n =  min (oferta, cerere)
- toata lumea are acces la informatiile legate de oferte si cereri, cit si la istoria tranzactiilor 
- cererile si ofertele pot fi modificate oricind, daca nu exista o tranzactie in desfasurare pe actiunile in cauza
Prin implementare se va intelege un sistem distribuit (server si clienti) in care clientii 
sint vinzatorii si cumparatorii. Prin simulare se va intelege un program in care vinzatorii si cumparatorii sint 
simulati prin fire de executie ce vor functiona pe baza unor algoritmi ce tin cont de informatiile 
comune (cereri si oferte , lista tranzactiilor efectuate).
```

## Descriere succinta

## Arhitecura
```
Tot code-ul se bazeaza pe pattern-ul Producer-Consumer. Avem 3 zone critice in code: un array de oferte, 
unul de cereri si un istoric. Cele trei array-uri o sa fie salvate intr-un singleton fiecare pentru a permite 
accesul global la acestea resurse. Orice transactie (cat si adaugarea unei noi oferte in market)
o sa fie salvata intr-un istoric.
```

### Actori
```
    Buyer - un type de thread care are ca si elemente de stare un array de stocks (List<Stocks>) 
            si o suma de bani (float)
          - acesta o sa scaneze array-ul de oferte si o sa caute sa satisfaca o cerere creata de ei. 
            Au o suma finita de bani deci se vor opri in momentul in care nu o sa mai aiba bani.
    
    Seller - un type de thread care scaneaza array-ul global de cereri si creeaza oferte pe care le propaga in array-ul
            de oferte. 
```

### Enititati

```
    Stock - id: long
            price: double
            owner: Thread

    Offer - id: long
            maker: Seller
            quantity: int
            stocks: List<Stocks>
            totalPrice: double
            type: String //Random shit - nu cred ca o sa ne trebuiasca

    Demand - id: long
             howMakesTheDemand: Buyer
             quantity: int
             type: String //Random shit - nu cred ca o sa ne trebuiasca
             
```
