 package cl.expled.web.page;
 
 import javax.servlet.http.HttpSession;
 import lib.security.session;
 import org.springframework.stereotype.Controller;
 import org.springframework.ui.Model;
 import org.springframework.web.bind.annotation.RequestMapping;
 import org.springframework.web.servlet.ModelAndView;
 
 @Controller
 public class mantencion
 {
   @RequestMapping({"/folio"})
   public ModelAndView caf(Model model, HttpSession httpSession) {
/*  18 */     session ses = new session(httpSession);
/*  19 */     if (ses.isValid()) {
/*  20 */       return new ModelAndView("redirect:/webApp/login");
     }
/*  22 */     model.addAttribute("content", "folio");
/*  23 */     model.addAttribute("javaScriptPage", "mantencion-folio");
/*  24 */     return new ModelAndView("layout/_main");
   }
   
   @RequestMapping({"/content/folio"})
   public ModelAndView cafContent(Model model, HttpSession httpSession) {
/*  29 */     session ses = new session(httpSession);
/*  30 */     if (ses.isValid()) {
/*  31 */       return new ModelAndView("redirect:/webApp/login");
     }
/*  33 */     return new ModelAndView("content/folio");
   }
 
   
   @RequestMapping({"/pfx"})
   public ModelAndView pfx(Model model, HttpSession httpSession) {
/*  39 */     session ses = new session(httpSession);
/*  40 */     if (ses.isValid()) {
/*  41 */       return new ModelAndView("redirect:/webApp/login");
     }
/*  43 */     model.addAttribute("content", "pfx");
/*  44 */     model.addAttribute("javaScriptPage", "mantencion-pfx");
     
/*  46 */     return new ModelAndView("layout/_main");
   }
   
   @RequestMapping({"/content/pfx"})
   public ModelAndView pfxContent(Model model, HttpSession httpSession) {
/*  51 */     session ses = new session(httpSession);
/*  52 */     if (ses.isValid()) {
/*  53 */       return new ModelAndView("redirect:/webApp/login");
     }
/*  55 */     return new ModelAndView("content/pfx");
   }
 
   
   @RequestMapping({"/usuario"})
   public ModelAndView usuario(Model model, HttpSession httpSession) {
/*  61 */     session ses = new session(httpSession);
/*  62 */     if (ses.isValid()) {
/*  63 */       return new ModelAndView("redirect:/webApp/login");
     }
/*  65 */     model.addAttribute("content", "usuario");
/*  66 */     model.addAttribute("javaScriptPage", "mantencion-usuarios");
/*  67 */     model.addAttribute("titulo", "Mantenci&oacute;n");
/*  68 */     model.addAttribute("subTitulo", "Usuario");
/*  69 */     return new ModelAndView("layout/_main");
   }
   
   @RequestMapping({"/content/usuario"})
   public ModelAndView usuarioContent(Model model, HttpSession httpSession) {
/*  74 */     session ses = new session(httpSession);
/*  75 */     if (ses.isValid()) {
/*  76 */       return new ModelAndView("redirect:/webApp/login");
     }
/*  78 */     return new ModelAndView("content/usuario");
   }
 
 
   
   @RequestMapping({"/productor"})
   public ModelAndView productor(Model model, HttpSession httpSession) {
/*  85 */     session ses = new session(httpSession);
/*  86 */     if (ses.isValid())
     {
/*  88 */       return new ModelAndView("redirect:/webApp/login");
     }
/*  90 */     model.addAttribute("content", "productor");
/*  91 */     model.addAttribute("javaScriptPage", "mantencion-productor");
/*  92 */     return new ModelAndView("layout/_main");
   }
 
   
   @RequestMapping({"/content/productor"})
   public ModelAndView contentProductor(Model mode, HttpSession httpSession) {
/*  98 */     session ses = new session(httpSession);
/*  99 */     if (ses.isValid())
     {
/* 101 */       return new ModelAndView("redirect:/webApp/login");
     }
/* 103 */     return new ModelAndView("content/productor");
   }
 
   
   @RequestMapping({"/diccionario"})
   public ModelAndView dicconario(Model model, HttpSession httpSession) {
/* 109 */     session ses = new session(httpSession);
/* 110 */     if (ses.isValid())
     {
/* 112 */       return new ModelAndView("redirect:/webApp/login");
     }
/* 114 */     model.addAttribute("content", "diccionario");
/* 115 */     model.addAttribute("javaScriptPage", "mantencion-diccionario");
/* 116 */     return new ModelAndView("layout/_main");
   }
   
   @RequestMapping({"/content/diccionario"})
   public ModelAndView contentDiccionario(Model mode, HttpSession httpSession) {
/* 121 */     session ses = new session(httpSession);
/* 122 */     if (ses.isValid())
     {
/* 124 */       return new ModelAndView("redirect:/webApp/login");
     }
/* 126 */     return new ModelAndView("content/diccionario");
   }
 
   
   @RequestMapping({"/mercado"})
   public ModelAndView mercado(Model model, HttpSession httpSession) {
/* 132 */     session ses = new session(httpSession);
/* 133 */     if (ses.isValid())
     {
/* 135 */       return new ModelAndView("redirect:/webApp/login");
     }
/* 137 */     model.addAttribute("content", "mercado");
/* 138 */     model.addAttribute("javaScriptPage", "mantencion-mercado");
/* 139 */     return new ModelAndView("layout/_main");
   }
   
   @RequestMapping({"/content/mercado"})
   public ModelAndView contentMercado(Model model, HttpSession httpSession) {
/* 144 */     session ses = new session(httpSession);
/* 145 */     if (ses.isValid())
     {
/* 147 */       return new ModelAndView("redirect:/webApp/login");
     }
/* 149 */     return new ModelAndView("content/mercado");
   }
 
   
   @RequestMapping({"/certificaciones"})
   public ModelAndView certificaciones(Model model, HttpSession httpSession) {
/* 155 */     session ses = new session(httpSession);
/* 156 */     if (ses.isValid())
     {
/* 158 */       return new ModelAndView("redirect:/webApp/login");
     }
/* 160 */     model.addAttribute("content", "certificaciones");
/* 161 */     model.addAttribute("javaScriptPage", "mantencion-certificaciones");
/* 162 */     return new ModelAndView("layout/_main");
   }
   
   @RequestMapping({"/content/certificaciones"})
   public ModelAndView contentCertificaciones(Model model, HttpSession httpSession) {
/* 167 */     session ses = new session(httpSession);
/* 168 */     if (ses.isValid())
     {
/* 170 */       return new ModelAndView("redirect:/webApp/login");
     }
/* 172 */     return new ModelAndView("content/certificaciones");
   }
 
   
   @RequestMapping({"/temporada"})
   public ModelAndView temporada(Model model, HttpSession httpSession) {
/* 178 */     session ses = new session(httpSession);
/* 179 */     if (ses.isValid())
     {
/* 181 */       return new ModelAndView("redirect:/webApp/login");
     }
/* 183 */     model.addAttribute("content", "temporada");
/* 184 */     model.addAttribute("javaScriptPage", "mantencion-temporada");
/* 185 */     return new ModelAndView("layout/_main");
   }
   
   @RequestMapping({"/content/temporada"})
   public ModelAndView contentTemporada(Model model, HttpSession httpSession) {
/* 190 */     session ses = new session(httpSession);
/* 191 */     if (ses.isValid())
     {
/* 193 */       return new ModelAndView("redirect:/webApp/login");
     }
/* 195 */     return new ModelAndView("content/temporada");
   }
 
   
   @RequestMapping({"/tipoProducto"})
   public ModelAndView tipoProducto(Model model, HttpSession httpSession) {
/* 201 */     session ses = new session(httpSession);
/* 202 */     if (ses.isValid())
     {
/* 204 */       return new ModelAndView("redirect:/webApp/login");
     }
/* 206 */     model.addAttribute("content", "tipoProducto");
/* 207 */     model.addAttribute("javaScriptPage", "mantencion-tipoProducto");
/* 208 */     return new ModelAndView("layout/_main");
   }
   
   @RequestMapping({"/content/tipoProducto"})
   public ModelAndView contentTipoProducto(Model model, HttpSession httpSession) {
/* 213 */     session ses = new session(httpSession);
/* 214 */     if (ses.isValid())
     {
/* 216 */       return new ModelAndView("redirect:/webApp/login");
     }
/* 218 */     return new ModelAndView("content/tipoProducto");
   }
 
   
   @RequestMapping({"/fuente"})
   public ModelAndView fuente(Model model, HttpSession httpSession) {
/* 224 */     session ses = new session(httpSession);
/* 225 */     if (ses.isValid())
     {
/* 227 */       return new ModelAndView("redirect:/webApp/login");
     }
/* 229 */     model.addAttribute("content", "fuente");
/* 230 */     model.addAttribute("javaScriptPage", "mantencion-fuente");
/* 231 */     return new ModelAndView("layout/_main");
   }
   
   @RequestMapping({"/content/fuente"})
   public ModelAndView contentFuente(Model model, HttpSession httpSession) {
/* 236 */     session ses = new session(httpSession);
/* 237 */     if (ses.isValid())
     {
/* 239 */       return new ModelAndView("redirect:/webApp/login");
     }
/* 241 */     return new ModelAndView("content/fuente");
   }
 
   
   @RequestMapping({"/limite"})
   public ModelAndView limite(Model model, HttpSession httpSession) {
/* 247 */     session ses = new session(httpSession);
/* 248 */     if (ses.isValid())
     {
/* 250 */       return new ModelAndView("redirect:/webApp/login");
     }
/* 252 */     model.addAttribute("content", "limite");
/* 253 */     model.addAttribute("javaScriptPage", "mantencion-limite");
/* 254 */     return new ModelAndView("layout/_main");
   }
   
   @RequestMapping({"/content/limite"})
   public ModelAndView contentLimite(Model model, HttpSession httpSession) {
/* 259 */     session ses = new session(httpSession);
/* 260 */     if (ses.isValid())
     {
/* 262 */       return new ModelAndView("redirect:/webApp/login");
     }
/* 264 */     return new ModelAndView("content/limite");
   }
 }