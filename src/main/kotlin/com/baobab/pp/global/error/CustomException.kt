package com.baobab.pp.global.error

class CustomException(val error: CustomError, vararg val args: Any?) : RuntimeException(error.message.format(args))