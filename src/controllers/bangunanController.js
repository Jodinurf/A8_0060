const Bangunan = require('../models/bangunanModels');
const { update } = require('../models/mahasiswaModels');

const BangunanController = {
    getAllBangunan : (req, res) => {
        Bangunan.getAll((err, results) => {
            if (err) {
                console.error('Error fetching bangunan:', err); // Log error
                res.status(500).json({
                    status: false,
                    message: "Internal Server Error",
                });
            } else if (results.length === 0) {
                res.status(200).json({
                    message: 'Data Bangunan Belum ada'
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

    getBangunanById: (req, res) => {
        Bangunan.getById(req.params.id, (err, result) => {
            if (err) {
                res.status(500).json({
                    status: false,
                    message: "Internal Server Error",
                });
            } else if (!result) {
                res.status(404).json({
                    status: false,
                    message: "Bangunan tidak ditemukan",
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

    createBangunan: (req, res) => {
        Bangunan.create(req.body, (err, results) => {
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

    updateBangunan: (req, res) => {
        Bangunan.update(req.params.id, req.body, (err, results) => {
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

    deleteBangunan: (req, res) => {
        Bangunan.delete(req.params.id, (err, results) => {
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
                    message: 'Data telah dihapus',
                    data: results
                });
            }
        });
    },
}

module.exports = BangunanController;