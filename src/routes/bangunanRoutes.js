const BangunanController = require('../controllers/bangunanController');
const express = require('express');
const router = express.Router();

router.get('/', BangunanController.getAllBangunan);
router.get('/:id', BangunanController.getBangunanById);
router.post('/store', BangunanController.createBangunan);
router.delete('/:id', BangunanController.deleteBangunan)
router.put('/:id', BangunanController.updateBangunan);

module.exports = router;