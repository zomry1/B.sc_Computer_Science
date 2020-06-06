﻿using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;
using System.Web.Routing;

namespace EX3
{
    public class RouteConfig
    {
        public static void RegisterRoutes(RouteCollection routes)
        {
            routes.IgnoreRoute("{resource}.axd/{*pathInfo}");

            routes.MapRoute("display", "display/{ip}/{port}/{time}",
            defaults: new { controller = "First", action = "display" , time = UrlParameter.Optional});

            routes.MapRoute(
                name: "save",
                url: "save/{ip}/{port}/{hz}/{length}/{file}",
                defaults: new { controller = "First", action = "save" }
            );

            routes.MapRoute(
                name: "Default",
                url: "{controller}/{action}/{id}",
                defaults: new { controller = "First", action = "Index", id = UrlParameter.Optional }
            );
        }
    }
}
