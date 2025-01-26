const connection = require('../config/db');

const Kamar = {
    getAll: (callback) => {
        const query = 
        'SELECT k.id_kamar, k.nomor_kamar, k.id_bangunan, b.nama_bangunan, b.alamat, k.kapasitas, k.status_kamar FROM kamar k JOIN bangunan b ON k.id_bangunan = b.id_bangunan';
        connection.query(query, (err, results) => {
            if (err) {
                console.error('Error fetching kamar:', err);
                return callback(err, null);
            }
            callback(null, results);
        });
    },

    getById: (id, callback) => {
        const query = 'SELECT * FROM kamar WHERE id_kamar = ?';
        connection.query(query, [id], (err, results) => {
            if (err) {
                console.error(`Error fetching kamar with id ${id}:`, err);
                return callback(err, null);
            }
            callback(null, results[0]);
        });
    },

    create: (data, callback) => {
        const query = 'INSERT INTO kamar SET ?';
        connection.query(query, data, (err, results) => {
            if (err) {
                console.error('Error creating kamar:', err);
                return callback(err, null);
            }
            callback(null, results.insertId);
        });
    },

    update: (id, data, callback) => {
        const query = 'UPDATE kamar SET ? WHERE id_kamar = ?';
        connection.query(query, [data, id], (err, results) => {
            if (err) {
                console.error(`Error updating kamar with id ${id}:`, err);
                return callback(err, null);
            }
            callback(null, results);
        });
    },

    delete: (id, callback) => {
        const query = 'DELETE FROM kamar WHERE id_kamar = ?';
        connection.query(query, [id], (err, results) => {
            if (err) {
                console.error(`Error deleting kamar with id ${id}:`, err);
                return callback(err, null);
            }
            callback(null, results);
        });
    }
};

module.exports = Kamar;