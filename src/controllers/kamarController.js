const Kamar = require('../models/kamarModels');

const KamarController = {
    getAllKamar: (req, res) => {
        Kamar.getAll((err, results) => {
            if (err) {
                console.error('Error fetching kamar:', err);
                res.status(500).json({
                    status: false,
                    message: "Internal Server Error",
                });
            } else if (results.length === 0) {
                res.status(200).json({
                    message: 'Data Kamar Belum ada'
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

    getKamarById: (req, res) => {
        Kamar.getById(req.params.id, (err, result) => {
            if (err) {
                res.status(500).json({
                    status: false,
                    message: "Internal Server Error",
                });
            } else if (!result) {
                res.status(404).json({
                    status: false,
                    message: "Kamar tidak ditemukan",
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

    createKamar: (req, res) => {
        Kamar.create(req.body, (err, results) => {
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

    updateKamar: (req, res) => {
        Kamar.update(req.params.id, req.body, (err, results) => {
            if (err) {
                res.status(500).json({
                    status: false,
                    message: "Internal Server Error",
                    error: err,
                });
            } else {
                res.status(200).json({
                    message: 'Data telah diupdate',
                    data: results
                });
            }
        });
    },

    deleteKamar: (req, res) => {
        Kamar.delete(req.params.id, (err, results) => {
            if (err) {
                res.status(500).json({
                    status: false,
                    message: "Internal Server Error",
                    error: err,
                });
            } else {
                res.status(200).json({
                    message: 'Data telah dihapus',
                    data: results
                });
            }
        });
    }
};

module.exports = KamarController;