let geocaches = [{ id: 1, nom: "Sous le pont", proprietaire: "John", latitude: "5.303", longitude: "2.132" }, { id: 2, nom: "Sous la maison", proprietaire: "Michel", latitude: "8.303", longitude: "-2.132" }, { id: 3, nom: "Sur ton daron", proprietaire: "Pocahonta", latitude: "80.303", longitude: "2.134132" }];

const express = require('express');

const app = express();
app.use(express.json());
app.use(express.urlencoded());

app.get('/geocache', (requete, reponse) => {
    reponse.send(geocaches);
});

app.post('/geocache/ajouter', (requete, reponse) => {
    const geocache = requete.body;
    geocache.id = geocaches.length + 1;
    geocaches.push(geocache);
    reponse.send(geocache);
});

app.get('/geocache/:id', (requete, reponse)=>{
    let geocache = null;
    if(trouvaille = requete.url.match(/^\/geocache\/([0-9]+)\/?$/)){
        geocache = geocaches.find(m => m.id === parseInt(trouvaille[1]));
    }

    if (!geocache){
        return reponse.status(404).send("Geocache Introuvable")
    }

    reponse.send(geocache);
});

app.listen(3000);
