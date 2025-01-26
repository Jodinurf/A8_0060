const connection = require('../config/db');

const formatDateToDMY = (date) => {
    if (!date) return null;
    const jsDate = new Date(date);
    const day = String(jsDate.getDate()).padStart(2, '0');
    const month = String(jsDate.getMonth() + 1).padStart(2, '0'); // Bulan dimulai dari 0
    const year = jsDate.getFullYear();
    return `${day}-${month}-${year}`;
};

const convertDMYToYMD = (date) => {
    if (!date) return null;
    const [day, month, year] = date.split('-');
    return `${year}-${month}-${day}`;
};

const PembayaranSewa = {
    getAll: (callback) => {
        connection.query('SELECT * FROM pembayaransewa', (err, results) => {
            if (err) {
                console.error('Error fetching pembayaran sewa:', err);
                return callback(err, null);
            }
            const formattedResults = results.map(result => ({
                ...result,
                tanggal_pembayaran: formatDateToDMY(result.tanggal_pembayaran),
            }));
            callback(null, formattedResults);
        });
    },

    getById: (id, callback) => {
        connection.query('SELECT * FROM pembayaransewa WHERE id_pembayaran = ?', [id], (err, results) => {
            if (err) {
                console.error(`Error fetching pembayaran sewa with id ${id}:`, err);
                return callback(err, null);
            }
            const result = results[0];
            if (result) {
                result.tanggal_pembayaran = formatDateToDMY(result.tanggal_pembayaran);
            }
            callback(null, result);
        });
    },
    
        getByIdMhs: (id, callback) => {
        const query = 'SELECT * FROM pembayaransewa WHERE id_mahasiswa = ? LIMIT 1';
        connection.query(query, [id], (err, results) => {
            if (err) {
                console.error(`Error fetching pembayaran sewa with id_mahasiswa ${id}:`, err);
                return callback(err, null);
            }
            if (results.length > 0) {
                const result = results[0];
                result.tanggal_pembayaran = formatDateToDMY(result.tanggal_pembayaran);
                callback(null, result);
            } else {
                callback(null, null);
            }
        });
    },

    create: (data, callback) => {
        if (data.tanggal_pembayaran) {
            data.tanggal_pembayaran = convertDMYToYMD(data.tanggal_pembayaran);
        }
        const query = 'INSERT INTO pembayaransewa SET ?';
        connection.query(query, data, (err, results) => {
            if (err) {
                console.error('Error creating pembayaran sewa:', err);
                return callback(err, null);
            }
            callback(null, results.insertId);
        });
    },

    update: (id, data, callback) => {
        if (data.tanggal_pembayaran) {
            data.tanggal_pembayaran = convertDMYToYMD(data.tanggal_pembayaran);
        }
        const query = 'UPDATE pembayaransewa SET ? WHERE id_pembayaran = ?';
        connection.query(query, [data, id], (err, results) => {
            if (err) {
                console.error(`Error updating pembayaran sewa with id ${id}:`, err);
                return callback(err, null);
            }
            callback(null, results);
        });
    },

    delete: (id, callback) => {
        const query = 'DELETE FROM pembayaransewa WHERE id_pembayaran = ?';
        connection.query(query, [id], (err, results) => {
            if (err) {
                console.error(`Error deleting pembayaran sewa with id ${id}:`, err);
                return callback(err, null);
            }
            callback(null, results);
        });
    }
};

module.exports = PembayaranSewa;
