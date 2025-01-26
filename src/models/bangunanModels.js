const connection = require('../config/db'); // Import the database connection

const Bangunan = {
    getAll: (callback) => {
        const query = 'SELECT * FROM bangunan';
        connection.query(query, (err, results) => {
            if (err) {
                console.error('Error fetching bangunan:', err);
                return callback(err, null);
            }
            callback(null, results);
        });
    },

    getById: (id, callback) => {
        const query = 'SELECT * FROM bangunan WHERE id_bangunan = ?';
        connection.query(query, [id], (err, results) => {
            if (err) {
                console.error(`Error fetching bangunan with id ${id}:`, err);
                return callback(err, null);
            }
            callback(null, results[0]);
        });
    },

    create: (data, callback) => {
        const query = 'INSERT INTO bangunan SET ?';
        connection.query(query, data, (err, results) => {
            if (err) {
                console.error('Error creating bangunan:', err);
                return callback(err, null);
            }
            callback(null, results.insertId);
        });
    },

    update: (id, data, callback) => {
        const query = 'UPDATE bangunan SET ? WHERE id_bangunan = ?';
        connection.query(query, [data, id], (err, results) => {
            if (err) {
                console.error(`Error updating bangunan with id ${id}:`, err);
                return callback(err, null);
            }
            callback(null, results);
        });
    },

    delete: (id, callback) => {
        const query = 'DELETE FROM bangunan WHERE id_bangunan = ?';
        connection.query(query, [id], (err, results) => {
            if (err) {
                console.error(`Error deleting bangunan with id ${id}:`, err);
                return callback(err, null);
            }
            callback(null, results);
        });
    }
};

module.exports = Bangunan;