const PembayaranSewa = require('../models/pembayaranSewaModels')

const PembayaranSewaController = {
    getAllPembayaranSewa: (req, res) => {
        PembayaranSewa.getAll((err, results) => {
            if (err) {
                console.error('Error fetching pembayaran sewa:', err)
                res.status(500).json({
                    status: false,
                    message: "Internal Server Error",
                })
            } else if (results.length === 0) {
                res.status(200).json({
                    message: 'Data Pembayaran Sewa Belum ada'
                })
            } else {
                return res.status(200).json({
                    status: true,
                    message: "Success",
                    data: results,
                })
            }
        })
    },

    getPembayaranSewaById: (req, res) => {
        PembayaranSewa.getById(req.params.id, (err, result) => {
            if (err) {
                res.status(500).json({
                    status: false,
                    message: "Internal Server Error",
                })
            } else if (!result) {
                res.status(404).json({
                    status: false,
                    message: "Pembayaran Sewa tidak ditemukan",
                })
            } else {
                res.status(200).json({
                    status: true,
                    message: "Success",
                    data: result,
                })
            }
        })
    },

    getPembayaranSewaByIdMhs: (req, res) => {
        PembayaranSewa.getByIdMhs(req.params.id, (err, result) => {
            if (err) {
                res.status(500).json({
                    status: false,
                    message: "Internal Server Error",
                })
            } else if (!result) {
                res.status(404).json({
                    status: false,
                    message: "Pembayaran Sewa tidak ditemukan",
                })
            } else {
                res.status(200).json({
                    status: true,
                    message: "Success",
                    data: result,
                })
            }
        })
    },

    createPembayaranSewa: (req, res) => {

        PembayaranSewa.create(req.body, (err, results) => {
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

    updatePembayaranSewa: (req, res) => {
        PembayaranSewa.update(req.params.id, req.body, (err, results) => {
            if (err) {
                res.status(500).json({
                    status: false,
                    message: "Internal Server Error",
                    error: err,
                })
            } else {
                res.status(200).json({
                    message: 'Data telah diperbarui',
                    data: results
                })
            }
        })
    },

    deletePembayaranSewa: (req, res) => {
        PembayaranSewa.delete(req.params.id, (err, results) => {
            if (err) {
                res.status(500).json({
                    status: false,
                    message: "Internal Server Error",
                    error: err,
                })
            } else {
                res.status(200).json({
                    message: 'Data telah dihapus',
                    data: results
                })
            }
        })
    },
};

module.exports = PembayaranSewaController;