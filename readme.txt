# COME USARE FLOORPLANS #

# CARICA un file/immagine di una pianta di un edificio di nome NOME.jpg (o un'altra estensione di foto)
# SALVA  due FILE XML separati con la mappa creata, di nome NOME.xml e NOME_topological.xml NELLA STESSA CARTELLA DEL FILE ORIGINARIO
# SE questi file ESISTONO vengono SOVRASCRITTI

# COME USARLO #

1. Carica immagine con l'apposito pulsante

2. imposta la scala dell'immagine/xml (barretta di scala corrisponde ad 1 porta, ~ 90cm, ed è visualizzata a dx tra i pulsanti e per caricare e salvare. La lunghezza di quella barretta deve corrispondere alla lunghezza della porta nell'immagine). La scala si modifica con i pulsanti 'n' e 'm'.

3. Disegnare le stanze. LE STANZE SI DISEGNANO IN SENSO ANTIORARIO (CCW).

	3.1 Si sceglie l'etichetta della stanza da inserire (i pallini a dx, quando una etichetta è selezionata il bordo del suo pallino cambia colore.). 
	Quando clicchi sull'immagine verrà inserito un pallino che corrisponde ad una nuova stanza. 
	Il pallino va messo nel CENTRO della stanza nuova. 
	Se fai un errore BACKSPACE cancella il pallino appena messo

	3.2 Si disegna la stanza appena inserita. Il sistema non ti farà inserire una nuova stanza fino a che non avrai creato un poligono chiuso che rappresenta la geometria della stanza inserita. Il primo punto inserito (e di conseguenza anche l'ultimo punto inserito sono identificati in ROSSO). Quando una stanza è ancora "aperta", ovvero non è associato un poligono ad essa, il colore del pallino della stanza è ARANCIONE. Quando una stanza è "chiusa", e quindi puoi inserire una nuova stanza, il colore è NERO.
	Per inserire una porta (come PUNTO) premi 'c' per una porta ESPLICITA e 'C' per una porta implicita.
	Se devi connettere due stanze, alla prima stanza delle due che disegni premi 'c/C' ed inserici una porta. alla seconda stanza che disegni premi sempre 'c/C' ed CLICCA SULLO STESSO PUNTO/PORTA. IL SISTEMA AUTOMATICAMENTE CREERA' UN COLLEGAMENTO TRA LE DUE STANZE: SE NON LO FA NON HAI SELEZIONATO LA PORTA.
	Se inserisci un punto per sbaglio, premi BACKSPACE e torni indietro.

4. Inserisci i gruppi. Un gruppo di stanze sono un'insieme di stanze che hanno la stessa funzione (es: Ingresso, o AREA AMMINISTRATIVA) e devono essere tutte COLLEGATE TRA LORO (se ci sono più gruppi di stanze che hanno la stessa funzione in punti diversi degli edifici, ci saranno più gruppi con lo stesso nome). UNA STANZA PUO' APPARTENERE A PIU' DI UN GRUPPO. UNA VOLTA INIZIATO AD AGGIUNGERE I GRUPPI NON SI PUO' PIU' TORNARE INDIETRO AD AGGIUNGERE LE STANZE.
	
	4.1 INSERISCI IL NOME DEL GRUPPO (da tastiera, ti viene visualizzato nel menù a destra)
	4.2 CLICCA SUI PALLINI DELLE STANZE CHE VUOI AGGIUNGERE: IL COLORE DEL LORO BORDO CAMBIERA', in base al colore del pallino.
	4.3 Quando hai finito di aggiungere tutte le stanze ad un gruppo AGGIUNGI UN NUOVO GRUPPO e torna a 4.1.

5. SALVA ed ESCI. CONSIGLIO: SALVA TANTE VOLTE ANCHE DURANTE L'AGGIUNGA DELLE STANZE e quando inizi a mettere i gruppi, tanto sovrascrive tutto (e se qualcosa crasha almeno c'è un po' di output da usare).



# ELENCO DEI COMANDI #

*	BACKSPACE : Elimina l'utima cosa fatta.
*	q | Q : Riduce lo ZOOM e mostra solo la mappa TOPOLOGICA. Serve per vedere lo stato del lavoro attuale e per controllare di non essersi dimenticati nulla. Durante questa fase non puoi editare nulla. Premi di nuovo 'q' per ritornare alla modalità di editing.
*	i | I : Mostra o nasconde l'immagine, per andare a vedere i punti messi se sono precisi o meno.
* 	c : inserisce una porta ESPLICITA
*	C : inserisce una porta IMPLICITA
*	n | M : riduce ed aumenta la "scala" dell'xml (ovvero a quanti pixel dell'immagine corrispondono 90cm nella pianta).
*	a | s | d | w (A | S | D | W) : MUOVONO la mappa a destra-sinistra-su-giu, in pratica ti fanno muovere nella mappa mentre editi. Sono gli stessi pulsanti di FIFA e di altri giochi per pc, al posto dei tasti direzionali.


# BUG E COSE DA RISOLVERE e IMPLEMENTARE #

V : FATTE
X : NON FATTE
X	i. se inserici un punto e una porta e poi fai "back" crasha.
X	ii. CARICARE una mappa parziale dentro il sistema per permettere di andare avanti da quella
X	iii. quando aggiunge una porta implicita lo dice nei linesegment, dovrebbe dirlo anche nei portals. Controllare che sia consistente.
X	iv. Se mi dimentico di connettere tra loro due porte cosa succede? crasha?
X	v. Migliorare le funzionalità di allineamenot tra i vari punti.
X	vi. le porte sono trattate come un punto unico. Andrebbero trattate come un segmento.
X	vii. Bisognerebbe fare un tool alternativo al "drawer" che carica una mappa e ti permette di editarla. Di spostare i punti, di aggiungere togliere porte e cose simili.
X	viii. Bisognerebbe fare un tool alternativo al drawer che ti carica una mappa e ti permette di aggiungere nuove informazioni (tipo i gruppi, praticamente di dare il nome ad una relazione e poi cliccare tutte le stanze che posseggono quella relazione).

