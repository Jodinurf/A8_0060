const express = require('express');
const app = express();
const port = 3000;

const mahasiswaRoutes = require('./src/routes/mahasiswaRoutes');
const bangunanRoutes = require('./src/routes/bangunanRoutes');
const kamarRoutes = require('./src/routes/kamarRoutes');
const pembayaranSewaRoutes = require('./src/routes/pembayaranSewaRoutes');

const cors = require('cors');
app.use(cors());

const bodyParser = require('body-parser');
app.use(bodyParser.urlencoded({ extended: false }));
app.use(bodyParser.json());

app.use('/api/mahasiswa', mahasiswaRoutes);
app.use('/api/bangunan', bangunanRoutes);
app.use('/api/kamar', kamarRoutes);
app.use('/api/pembayaran', pembayaranSewaRoutes);

app.listen(port, () => {
    console.log(`Server is running on port localhost:${port}`);
});