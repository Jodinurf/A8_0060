const PembayaranSewaController = require('../controllers/pembayaranSewaController');
const express = require('express');
const router = express.Router();

router.get('/', PembayaranSewaController.getAllPembayaranSewa);
router.get('/:id', PembayaranSewaController.getPembayaranSewaById);
router.get('/mahasiswa/:id', PembayaranSewaController.getPembayaranSewaByIdMhs);
router.post('/store', PembayaranSewaController.createPembayaranSewa);
router.delete('/:id', PembayaranSewaController.deletePembayaranSewa)
router.put('/:id', PembayaranSewaController.updatePembayaranSewa);

module.exports = router;