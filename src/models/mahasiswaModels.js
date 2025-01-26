const connection = require('../config/db'); // Import the database connection

const Mahasiswa = {
    getAll: (callback) => {
        const query = 'SELECT * FROM mahasiswa';
        connection.query(query, (err, results) => {
            if (err) {
                console.error('Error fetching mahasiswa:', err);
                return callback(err, null);
            }
            callback(null, results);
        });
    },

    getById: (id, callback) => {
        const query = 'SELECT * FROM mahasiswa WHERE id_mahasiswa = ?';
        connection.query(query, [id], (err, results) => {
            if (err) {
                console.error(`Error fetching mahasiswa with id ${id}:`, err);
                return callback(err, null);
            }
            callback(null, results[0]);
        });
    },

    create: (data, callback) => {
        const query = 'INSERT INTO mahasiswa SET ?';
        connection.query(query, data, (err, results) => {
            if (err) {
                console.error('Error creating mahasiswa:', err);
                return callback(err, null);
            }
            callback(null, results.insertId);
        });
    },

    update: (id, data, callback) => {
        const query = 'UPDATE mahasiswa SET ? WHERE id_mahasiswa = ?';
        connection.query(query, [data, id], (err, results) => {
            if (err) {
                console.error(`Error updating mahasiswa with id ${id}:`, err);
                return callback(err, null);
            }
            callback(null, results);
        });
    },

    delete: (id, callback) => {
        const query = 'DELETE FROM mahasiswa WHERE id_mahasiswa = ?';
        connection.query(query, [id], (err, results) => {
            if (err) {
                console.error(`Error deleting mahasiswa with id ${id}:`, err);
                return callback(err, null);
            }
            callback(null, results);
        });
    }
};

module.exports = Mahasiswa;
