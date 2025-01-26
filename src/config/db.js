const db = require('mysql2');

const connection = db.createConnection({
    host: 'localhost',
    user: 'root',
    password: '',
    database: 'pam_final_project'
});

connection.connect((err) => {
    if (err) {
        console.error('Error connecting to the database:', err);
        return;
    }
    console.log('Connected to the database');
});

module.exports = connection;