const Mahasiswa = require('../models/mahasiswaModels');

const MahasiswaController = {
    getAllMahasiswa: (req, res) => {
        Mahasiswa.getAll((err, results) => {
            if (err) {
                console.error('Error fetching mahasiswa:', err); // Log error
                res.status(500).json({
                    status: false,
                    message: "Internal Server Error",
                });
            } else if (results.length === 0) {
                res.status(200).json({
                    message: 'Data Mahasiswa Belum ada'
                });
            } else {
                return res.status(200).json({
                    status: true,
                    message: "Success",
                    data: results,
                });
            }
        });
    },
    
    getMahasiswaById: (req, res) => {
        Mahasiswa.getById(req.params.id, (err, result) => {
            if (err) {
                res.status(500).json({
                    status: false,
                    message: "Internal Server Error",
                });
            } else if (!result) {
                res.status(404).json({
                    status: false,
                    message: "Mahasiswa tidak ditemukan",
                });
            } else {
                res.status(200).json({
                    status: true,
                    message: "Success",
                    data: result,
                });
            }
        });
    },

    createMahasiswa: (req, res) => {
        Mahasiswa.create(req.body, (err, results) => {
            if (err) {
                res.status(500).json({
                    status: false,
                    message: "Internal Server Error",
                    error: err,
                });
            } else {
                res.status(201).json({
                    message: 'Data telah dibuat',
                    data: results
                });
            }
        });
    },

    updateMahasiswa: (req, res) => {
        Mahasiswa.update(req.params.id, req.body, (err, results) => {
            if (err) {
                res.status(500).json({
                    status: false,
                    message: 'Internal server error'
                });
            } else if (results.affectedRows === 0) {
                res.status(404).json({
                    status: false,
                    message: 'Data tidak ditemukan'
                });
            } else {
                res.status(200).json({
                    status: true,
                    message: 'Data telah diupdate',
                    data: results
                });
            }
        });
    },

    deleteMahasiswa: (req, res) => {
        Mahasiswa.delete(req.params.id, (err, results) => {
            if (err) {
                res.status(500).json({
                    status: false,
                    message: 'Internal server error'
                });
            } else if (results.affectedRows === 0) {
                res.status(404).json({
                    status: false,
                    message: 'Data tidak ditemukan'
                });
            } else {
                res.status(200).json({
                    status: true,
                    message: 'Data has been deleted',
                    data: results
                });
            }
        });
    },
};


module.exports = MahasiswaController;